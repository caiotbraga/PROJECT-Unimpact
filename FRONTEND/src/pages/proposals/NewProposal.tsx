import React, { FormEvent, useContext, useEffect, useRef, useState } from "react";

import FormFloatingInput from "../../components/FormsFields/FormInput";
import FormSelect from "../../components/FormsFields/FormSelect";

import axios from "axios";
import { getRequestBasicConfig, handleLogOutUser } from "../../utils";
import { AlertsContext } from "../../App";

import BasePage from "../../components/BasePage";
import { fetchGroupsByParticipate } from "../../services/GroupServices";
import LabeledButton from "../../components/Buttons/LabeledButton";
import HighlightedLabel from "../../components/HighlightedLabel";
import { postProposal, updateProposalStatus } from "../../services/ProposalServices";
import { TProposal } from "../../types/ProposalTypes";

export interface TNewProposal {
    mainObjective: string;
    numberBeneficiaries: number;
    ownerIdentification: string;
    ownerType: string;
    specificObjectives: string[];
    summary: string;
    targetPublic: string[];
    title: string;
    groupIds?: number[];
    startDate: Date; 
    endDate: Date; 
    numberOfPeople: number;
}

type TFormErrors = {
    title?: string;
    summary?: string;
    mainObjective?: string;
    targetPublic?: string;
    responsibleGroup?: string;
    dates?: string; 
    numberOfPeople?: string; 
};

type TAvailableGroup = {
    id: number;
    name: string;
};

const NewProposal = () => {
    const { addToast } = useContext(AlertsContext);

    const inStartDate = useRef<HTMLInputElement | null>(null);
    const inEndDate = useRef<HTMLInputElement | null>(null);

    const inTitle = useRef<HTMLInputElement | null>(null);
    const inSummary = useRef<HTMLTextAreaElement | null>(null);

    const inMainObjective = useRef<HTMLInputElement | null>(null);
    const inSpecificObjective1 = useRef<HTMLInputElement | null>(null);
    const inSpecificObjective2 = useRef<HTMLInputElement | null>(null);
    const inSpecificObjective3 = useRef<HTMLInputElement | null>(null);

    const inTargetPublic = useRef<HTMLInputElement | null>(null);
    const inQntPeople = useRef<HTMLInputElement | null>(null);

    const slctResponsibleGroup = useRef<HTMLSelectElement | null>(null);

    const newProposal = useRef<TNewProposal | null>(null);
    const userEmail = useRef<string>("");
    const ownerType = useRef<string>("");

    const [assignToGroup, setAssignToGroup] = useState(false);
    const [availableGroups, setAvailableGroups] = useState<TAvailableGroup[]>([]);

    const [formErrors, setFormErrors] = useState<TFormErrors>();

    const fetchUserEmail = () => {
        const userType = window.sessionStorage.getItem("user_type");
        if (!userType) return;

        const isJuridicalUser = userType === "ROLE_REQUESTING_MANAGER";

        const endPoint = `http://localhost:9090/person/${isJuridicalUser ? "juridical" : "physical"}/`;

        axios
            .get(endPoint, getRequestBasicConfig())
            .then(({ data }) => {
                userEmail.current = data.object.email;
            })
            .catch(() => {
                handleLogOutUser();
            });

        if (!isJuridicalUser) ownerType.current = "PHYSICAL_PERSON";
        else ownerType.current = "JURIDICAL_PERSON";
    };

    const isNewProposalValid = () => {
        if (!newProposal.current) return false;
    
        let isValid = true;
        let errors: TFormErrors = {};
    
        const proposal = newProposal.current;
    
        if (proposal.title === "") {
            errors.title = "Forneça um título";
            isValid = false;
        } else if (errors.title !== undefined) delete errors.title;
    
        if (proposal.summary === "") {
            errors.summary = "Descreva a proposta";
            isValid = false;
        } else if (errors.summary !== undefined) delete errors.summary;
    
        if (proposal.mainObjective === "") {
            errors.mainObjective = "Por favor, insira o objetivo principal";
            isValid = false;
        } else if (errors.mainObjective !== undefined) delete errors.mainObjective;
    
        if (proposal.targetPublic.length === 0) {
            errors.targetPublic = "Insira o público-alvo. Separe por vírgula caso haja mais de um";
            isValid = false;
        } else if (errors.targetPublic !== undefined) delete errors.targetPublic;
    
        if (assignToGroup && (!proposal.groupIds || proposal.groupIds.length === 0)) {
            errors.responsibleGroup = "Selecione o grupo responsável";
            isValid = false;
        } else if (errors.responsibleGroup !== undefined) delete errors.responsibleGroup;
    
        if (!proposal.startDate || !proposal.endDate || proposal.endDate < proposal.startDate) {
            errors.dates = "A data final deve ser posterior à data inicial";
            isValid = false;
        } else if (errors.dates !== undefined) {
            delete errors.dates;
        }
    
        if (!proposal.numberOfPeople || proposal.numberOfPeople <= 0) {
            errors.numberOfPeople = "Preencha o campo de quantidade de pessoas";
            isValid = false;
        } else if (errors.numberOfPeople !== undefined) {
            delete errors.numberOfPeople;
        }
    
        setFormErrors(errors);
    
        if (!isValid) {
            addToast({
                header: "Erro!",
                content: "Existem campos obrigatórios não preenchidos ou preenchidos incorretamente.",
                isSuccess: false,
            });
        }
    
        return isValid;
    };
    

    const constructNewProposal = () => {
        const specificObjectives: string[] = [];
        
        if (inSpecificObjective1.current?.value !== "" && inSpecificObjective1.current)
            specificObjectives.push(inSpecificObjective1.current?.value);
        if (inSpecificObjective2.current?.value !== "" && inSpecificObjective2.current)
            specificObjectives.push(inSpecificObjective2.current?.value);
        if (inSpecificObjective3.current?.value !== "" && inSpecificObjective3.current)
            specificObjectives.push(inSpecificObjective3.current?.value);
        
        newProposal.current = {
            mainObjective: inMainObjective.current?.value || "",
            numberBeneficiaries: Number(inQntPeople.current?.value) || -1,
            ownerIdentification: userEmail.current,
            ownerType: ownerType.current,
            specificObjectives: specificObjectives,
            summary: inSummary.current?.value || "",
            targetPublic: inTargetPublic.current?.value.split(",") || [],
            title: inTitle.current?.value || "",
            startDate: inStartDate.current?.value ? new Date(inStartDate.current.value) : new Date(),
            endDate: inEndDate.current?.value ? new Date(inEndDate.current.value) : new Date(),
            numberOfPeople: Number(inQntPeople.current?.value) || -1, 
        };
        
        if (newProposal.current) {
            if (assignToGroup && slctResponsibleGroup.current?.value !== "none") {
                newProposal.current.groupIds = [Number(slctResponsibleGroup.current?.value)];
            }
        }
    };
    
    const fetchAvailableGroups = () => {
        fetchGroupsByParticipate(
            ({ data }) => {
                const aux: TAvailableGroup[] = [];

                data.object.forEach((group: any, i: number) => {
                    aux[i] = {
                        id: group.id,
                        name: group.name,
                    };
                });

                setAvailableGroups(aux);
            },
            () => {
                addToast({
                    header: "Erro!",
                    content: "Ocorreu um erro ao atualizar a lista de grupos.",
                });
            }
        );
    };

    const postNewProposal = () => {
        if (!newProposal.current || !formErrors) return;

        let data = newProposal.current;

        if (!assignToGroup) delete data.groupIds;

        postProposal(
            ({data: postData}) => {
                const postedProposal: TProposal = postData.object
                addToast({
                    header: "Sucesso!",
                    content: "A solicitação foi aberta com sucesso. Aguarde a avaliação da UNICAP.",
                });
                updateProposalStatus(
                    ({ data }) => {console.log(data)},
                    {
                        nextStatus: "IN_REVIEW",
                        stateId: postedProposal.actualState.id,
                    },
                    (e) => console.log(e.response)
                );
            },
            data,
            () => {
                addToast({
                    header: "Erro!",
                    content: "Ocorreu um erro ao abrir a solicitação. Tente novamente mais tarde",
                    isSuccess: false,
                });
            }
        );
    };

    const createNewProposal = (e: FormEvent) => {
        e.preventDefault();

        constructNewProposal();

        if (isNewProposalValid()) postNewProposal();
    };

    const handleCheckAssignToGroup = (e: React.ChangeEvent<HTMLSelectElement>) => {
        if (e.currentTarget.value === "none") setAssignToGroup(false);
        else setAssignToGroup(true);
    };

    useEffect(() => {
        fetchUserEmail();
        fetchAvailableGroups();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    return (
        <BasePage id="new-proposal-page">
            <div id="__instructions">
                <p className="medium_text">
                    Preencha cuidadosamente os campos abaixo. Favor, descreva sua proposta com claresa, não inclua
                    informações desnecessárias.
                </p>
            </div>
            <form id="formNewProposal" onSubmit={createNewProposal}>
                <div id="__proposal_title">
                    <input
                        ref={inTitle}
                        type="text"
                        id="newProposalTitle"
                        placeholder="Título da proposta"
                        className={`no-outline-focus ${formErrors?.title && "is-invalid"}`}
                    />
                    {formErrors?.title && <div className="invalid-feedback">{formErrors.title}</div>}
                </div>
                {/* <div className="__proposal_section">
                        <FormSelect
                            isDisabled
                            fRef={slctProposalArea}
                            options={["Educação", "Saúde", "Capacitação", "Religiosa"]}
                            label={"Área da proposta"}
                        />
                    </div> */}

                <ProposalSection sectionTitle="Faça um breve resumo da proposta">
                    <textarea
                        ref={inSummary}
                        className={`fill-input __text_area no-outline-focus ${formErrors?.summary && "is-invalid"}`}
                        name="proposaSummary"
                        id="inSummary"
                        cols={30}
                        rows={10}
                        placeholder="Utilize este campo para descrever a ideia e execução da proposta"></textarea>
                    {formErrors?.summary && <div className="invalid-feedback">{formErrors.summary}</div>}
                </ProposalSection>

                <ProposalSection sectionTitle="Em poucas palavras descreva:">
                    <div className="__form_row">
                        <FormFloatingInput
                            required
                            isInvalid={formErrors?.mainObjective !== undefined}
                            invalidFeedback={formErrors?.mainObjective}
                            fRef={inMainObjective}
                            id={"inMainObjective"}
                            label={"Objetivo principal"}
                            inputSize="fill"
                        />
                    </div>
                    <div className="__form_row">
                        <FormFloatingInput
                            fRef={inSpecificObjective1}
                            id={"inSecondaryObjective1"}
                            label={"Objetivo específico 1"}
                            inputSize="fill"
                        />
                    </div>
                    <div className="__form_row">
                        <FormFloatingInput
                            fRef={inSpecificObjective2}
                            id={"inSecondaryObjective2"}
                            label={"Objetivo específico 2"}
                            inputSize="fill"
                        />
                    </div>
                    <div className="__form_row mb-0">
                        <FormFloatingInput
                            fRef={inSpecificObjective3}
                            id={"inSecondaryObjective3"}
                            label={"Objetivo específico 3"}
                            inputSize="fill"
                        />
                    </div>
                </ProposalSection>

                <ProposalSection
                    sectionTitle="Qual o público alvo e a quantidade de pessoas a serem beneficiadas?"
                    bodyClassname="d-flex align-items-center">
                    <FormFloatingInput
                        required
                        isInvalid={formErrors?.targetPublic !== undefined}
                        invalidFeedback={formErrors?.targetPublic}
                        fRef={inTargetPublic}
                        id={"inTargetPublic"}
                        label={"Público alvo"}
                        placeholder={"Ex: professores, alunos"}
                        inputSize="large"
                    />
                    <FormFloatingInput
                        fRef={inQntPeople}
                        parentClassName="ms-4"
                        id={"inQntPeople"}
                        label={"Qtd. pessoas"}
                        inputSize="medium"
                        fieldType="number"
                    />
                </ProposalSection>

                <ProposalSection sectionTitle="Qual a data ou período de execução?">
                    <HighlightedLabel
                        size="small"
                        label={
                            "Selecione apenas a data inicial caso a execução da solicitação seja para apenas um dia."
                        }
                    />
                    <div className="d-flex align-items-center mt-2">
                        <FormFloatingInput
                            id={"start-date"}
                            label={"Data inicial"}
                            fieldType="date"
                            inputSize="undefined"
                            className="me-4"
                        />
                        <FormFloatingInput
                            id={"end-date"}
                            label={"Data final"}
                            fieldType="date"
                            inputSize="undefined"
                        />
                    </div>
                </ProposalSection>

                <ProposalSection sectionTitle="Deseja atribuir essa solicitação à um grupo?">
                    <FormSelect
                        isInvalid={formErrors?.responsibleGroup !== undefined}
                        invalidFeedback={formErrors?.responsibleGroup}
                        fRef={slctResponsibleGroup}
                        options={availableGroups.map((g) => g.name)}
                        values={availableGroups.map((g) => g.id.toString())}
                        label={"Grupo responsável"}
                        onChange={handleCheckAssignToGroup}
                    />
                </ProposalSection>

                <ProposalSection sectionTitle="Forneça informações relevantes para o atendimento da sua solicitação">
                    <textarea
                        ref={inSummary}
                        className={`fill-input __text_area no-outline-focus ${formErrors?.summary && "is-invalid"}`}
                        name="proposaSummary"
                        id="inSummary"
                        cols={30}
                        rows={10}
                        placeholder="Utilize este campo para informar à UNICAP informações que agreguem entendimento sobre o atendimento da solicitação"></textarea>
                    {formErrors?.summary && <div className="invalid-feedback">{formErrors.summary}</div>}
                </ProposalSection>
                <LabeledButton type={"submit"} className="d-block mx-auto">
                    Enviar proposta
                </LabeledButton>
            </form>
        </BasePage>
    );
};

interface IProposalSection {
    sectionTitle: string;
    bodyClassname?: string;
}

export const ProposalSection: React.FC<IProposalSection> = ({ sectionTitle, bodyClassname, children }) => {
    return (
        <div className="__proposal_section">
            <div className="__header">
                <p>{sectionTitle}</p>
            </div>
            <div className={`__body ${bodyClassname}`}>{children}</div>
        </div>
    );
};

export default NewProposal;
