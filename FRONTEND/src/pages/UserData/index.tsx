import React, { useContext, useEffect, useRef, useState } from "react";

import DeleteAccountconfirmationModal from "./DeleteAccountConfirmationModal";
import FormFloatingInput from "../../components/FormsFields/FormInput";
import PasswordConfirmationModal from "./PasswordConfirmationModel";
import FormSelect from "../../components/FormsFields/FormSelect";

import { AlertsContext } from "../../App";

import { TUserToBeUpdated } from "../../types/UserTypes";
import { getRequestBasicConfig } from "../../utils";

import axios from "axios";
import BasePage from "../../components/BasePage";
import PageTitle from "../../components/PageTitle";
import LabeledButton from "../../components/Buttons/LabeledButton";

const PhysicalUserDataPage: React.FC = () => {
    const [userData, setUserData] = useState<TUserToBeUpdated | null>(null);
    const { addToast } = useContext(AlertsContext);

    const inHousePhone = useRef<HTMLInputElement | null>(null);
    const inPhone = useRef<HTMLInputElement | null>(null);
    const inCEP = useRef<HTMLInputElement | null>(null);
    const inStreet = useRef<HTMLInputElement | null>(null);
    const inState = useRef<HTMLInputElement | null>(null);
    const inDistrict = useRef<HTMLInputElement | null>(null);
    const inHouseNumber = useRef<HTMLInputElement | null>(null);
    const inEmail = useRef<HTMLInputElement | null>(null);

    const [isWaitingForUserPassword, setIsWaitingForUserPassword] = useState(false);
    const [isWaitingForUserConfirmDeletion, setIsWaitingForUserConfirmDeletion] = useState(false);

    const fetchUserData = () => {
        axios
            .get(`http://localhost:9090/person/physical/`, getRequestBasicConfig())
            .then((response) => {
                setUserData(response.data.object);
            })
            .catch(() => {
                addToast({
                    header: "ERRO!",
                    content: "Não foi possível encontrar seus dados. Você será redirecionado.",
                    isSuccess: false,
                });
                setTimeout(() => (window.location.href = "/"), 3000);
            });
    };

    const updateUser = (_user: any) => {
        axios
            .patch(`http://localhost:9090/person/physical/patch/`, _user, getRequestBasicConfig())
            .then(() => {
                addToast({
                    header: "Sucesso!",
                    content: "Seus dados foram atualizados com sucesso",
                    isSuccess: true,
                });
            })
            .catch(() => {
                addToast({
                    header: "ERRO!",
                    content: "Não foi possível atualizar seus dados. Tente novamente mais tarde.",
                    isSuccess: false,
                });
            });
    };

    const deleteUserAccount = () => {
        axios.delete(`http://localhost:9090/person/physical`, getRequestBasicConfig()).then((response) => {
            if (response.status >= 200 && response.status <= 299) {
                window.location.href = "/";
            }
        });
    };

    const handleSubmit = (e?: React.FormEvent) => {
        if (e) {
            e?.preventDefault();
            setIsWaitingForUserPassword(true);
            return;
        } else setIsWaitingForUserPassword(false);

        if (!userData) return;

        const _user = {
            phones: [
                {
                    number: inHousePhone.current?.value || userData.phones[0].number,
                },
                userData.phones[1]
                    ? {
                          number: inPhone.current?.value || userData.phones[1].number,
                      }
                    : null,
            ],
            addresses: [
                {
                    cep: inCEP.current?.value || userData.addresses[0].cep,
                    street: inStreet.current?.value || userData.addresses[0].street,
                    state: inState.current?.value || userData.addresses[0].state,
                    district: inDistrict.current?.value || userData.addresses[0].district,
                    number: inHouseNumber.current?.value || userData.addresses[0].number,
                    country: "Brasil",
                },
            ],
            email: userData.email,
            password: "123",
        };

        updateUser(_user);
    };

    useEffect(() => {
        fetchUserData();
    }, []);

    return (
        <BasePage>
            <PageTitle title="Seus dados" />
            <form className="user-register-form" onSubmit={handleSubmit}>
                <div className="__form_row">
                    <FormFloatingInput
                        defaultValue={userData?.name}
                        id={"fullName"}
                        inputSize="large"
                        isReadOnly
                        label={"Nome"}
                    />
                    <FormFloatingInput defaultValue={userData?.cpf} id={"cpf"} isReadOnly label={"CPF"} />
                    <FormFloatingInput defaultValue={userData?.rg} id={"rg"} isReadOnly label={"RG"} />
                    {/* <FormFloatingInput
                        defaultValue={"Não informado"}
                        id={"emissorInstituition"}
                        isReadOnly
                        label={"Orgão emissor"}
                    /> */}
                </div>
                <div className="__form_row">
                    {/* <FormSelect
                        label="Estado civil"
                        options={["Solteiro(a)", "Casado(a)", "Viúvo(a)", "Divorciado(a)"]}
                        id={"civilStateSelect"}
                        isDisabled
                    /> */}
                    <FormFloatingInput
                        defaultValue={userData?.phones[0].number}
                        id={"housePhone"}
                        label={"Telefone"}
                        fRef={inHousePhone}
                    />
                    <FormFloatingInput
                        defaultValue={userData?.phones[1]?.number || ""}
                        id={"phone"}
                        label={"Telefone celular"}
                        fRef={inPhone}
                    />
                </div>
                <span className="subsection-title">Endereço</span>
                <div className="__form_row">
                    <FormFloatingInput
                        defaultValue={userData?.addresses[0].cep}
                        id={"cep"}
                        label={"CEP"}
                        fRef={inCEP}
                    />
                    <FormFloatingInput
                        defaultValue={userData?.addresses[0].street}
                        id={"street"}
                        inputSize={"large"}
                        label={"Rua"}
                        fRef={inStreet}
                    />
                    <FormFloatingInput
                        defaultValue={userData?.addresses[0].state}
                        id={"state"}
                        label={"Estado"}
                        fRef={inState}
                    />
                </div>
                <div className="__form_row">
                    <FormFloatingInput
                        defaultValue={userData?.addresses[0].district}
                        id={"district"}
                        label={"Bairro"}
                        fRef={inDistrict}
                    />
                    <FormFloatingInput
                        defaultValue={userData?.addresses[0].number}
                        id={"houseNumber"}
                        label={"Número / Complemento"}
                        fRef={inHouseNumber}
                    />
                </div>
                <span className="subsection-title">Informações de login</span>
                <div className="__form_row">
                    <FormFloatingInput
                        defaultValue={userData?.email}
                        id={"email"}
                        invalidFeedback="Forneça um e-mail válido"
                        label={"E-mail"}
                        fRef={inEmail}
                    />
                </div>
                <LabeledButton type={"submit"} className="mx-auto d-block mt-5">
                    Salvar
                </LabeledButton>
            </form>
            <div>
                <button
                    className="d-block text-center mx-auto"
                    style={{ fontSize: "16px", textDecoration: "underline", marginTop: "32px" }}
                >
                    Alterar senha de acesso
                </button>
                <button
                    onClick={() => setIsWaitingForUserConfirmDeletion(true)}
                    className="d-block mt-2 text-danger mx-auto"
                    style={{ fontSize: "14px", textDecoration: "underline" }}
                >
                    Excluir minha conta
                </button>
            </div>
            <PasswordConfirmationModal
                onConfirm={handleSubmit}
                onCancel={() => setIsWaitingForUserPassword(false)}
                show={isWaitingForUserPassword}
            />
            <DeleteAccountconfirmationModal
                onConfirm={deleteUserAccount}
                onCancel={() => setIsWaitingForUserConfirmDeletion(false)}
                show={isWaitingForUserConfirmDeletion}
            />
        </BasePage>
    );
};

export default PhysicalUserDataPage;
