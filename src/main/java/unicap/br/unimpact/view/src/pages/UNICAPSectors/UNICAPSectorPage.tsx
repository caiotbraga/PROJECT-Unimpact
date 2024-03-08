import axios from "axios";
import React, { useContext, useEffect, useRef, useState } from "react";
import { Button, Table } from "react-bootstrap";
import { useParams } from "react-router-dom";
import { AlertsContext } from "../../App";
import BasePage from "../../components/BasePage";
import LabeledButton from "../../components/Buttons/LabeledButton";
import ConfirmationModal from "../../components/ConfirmationModal";
import PageTitle from "../../components/PageTitle";
import Representative from "../../components/Representative";
import { fetchGroupById, removeGroupMember } from "../../services/GroupServices";
import { TGroup } from "../../types/GroupTypes";
import { getRequestBasicConfig } from "../../utils";

const UNICAPSectorPage = () => {
    const { addToast } = useContext(AlertsContext);
    let { sectorId } = useParams();

    const [sector, setSector] = useState<TGroup | null>(null);
    const [showDeleteUserModal, setShowDeleteUserModal] = useState(false);
    const [representativeToBeRemoved, setRepresentativeToBeRemoved] = useState<string | null>(null);

    const inNewGroupMemberEmail = useRef<HTMLInputElement | null>(null);

    const onCancelRepresentativeRemoval = () => {
        setShowDeleteUserModal(false);
        setRepresentativeToBeRemoved(null);
    };

    const onConfirmRepresentativeRemoval = () => {
        setShowDeleteUserModal(false);
        removeSectorMember();
        setRepresentativeToBeRemoved(null);
    };

    const removeSectorMember = () => {
        if (!representativeToBeRemoved || !sector) return;

        const memberBaseInfo = {
            groupId: sector.id,
            memberEmail: representativeToBeRemoved,
        };

        removeGroupMember(
            memberBaseInfo,
            () => {
                addToast({
                    header: "Sucesso!",
                    content: "O membro foi removido do grupo.",
                });
                fetchSectorData();
            },
            () => {
                addToast({
                    header: "Erro!",
                    content: "Ocorreu um erro ao remover o membro do grupo. Tente novamente mais tarde.",
                    isSuccess: false,
                });
            }
        );
    };

    const addMemberToSector = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()

        if (!inNewGroupMemberEmail.current || !sector) return;

        if (inNewGroupMemberEmail.current.value.trim() === "") {
            inNewGroupMemberEmail.current.classList.add("is-invalid");
            return;
        }

        axios
            .post(
                `http://localhost:9090/notification/invite?groupId=${sector.id}&notificationType=INVITE_GROUP&recipientsEmail=${inNewGroupMemberEmail.current.value}`,
                {},
                getRequestBasicConfig()
            )
            .then(() => {
                addToast({
                    header: "Sucesso!",
                    content: "A solicitação foi enviada.",
                    isSuccess: true,
                });
            })
            .catch((error: any) => {
                if (error.response.status === 404) {
                    addToast({
                        header: "Erro!",
                        content: "O e-mail inserido não percetence à um usuário UNIMPACT.",
                        isSuccess: false,
                    });
                } else {
                    addToast({
                        header: "Erro!",
                        content: "Ocorreu um erro ao adicionar o membro ao setor. Tente novamente mais tarde",
                        isSuccess: false,
                    });
                }
            })
            .finally(() => {
                fetchSectorData();
            });
    };

    const fetchSectorData = () => {
        fetchGroupById(
            Number(sectorId),
            ({ data }) => {
                setSector(data.object);
            },
            () => {
                addToast({
                    header: "Error!",
                    content: "Ocorreu um erro ao carregar os dados do setor. Tente novamente mais tarde.",
                    isSuccess: false,
                });
            }
        );
    };

    // const fetchSectorProjects = () => {
    //     fetchSectorProjects(sector.id, (({data}) => {

    //     }))
    // }

    useEffect(() => {
        fetchSectorData();
    }, []);

    if (!sector || !sectorId) return <></>;

    return (
        <BasePage>
            <ConfirmationModal
                title={"Remover membro?"}
                content={"Deseja realmente remove o representante? Essa ação não pode ser desfeita."}
                show={showDeleteUserModal}
                onCancel={onCancelRepresentativeRemoval}
                onConfirm={onConfirmRepresentativeRemoval}
                invertButtons
                isWarning
            />
            <PageTitle title={sector.name} className="mb-3">
                <span className="medium-text secondary-text me-4">
                    <span className="__detail_name">Código: </span>
                    {sector.id}
                </span>
                <span className="medium-text secondary-text ">
                    <span className="__detail_name">Membros: </span>
                    {sector.members.length}
                </span>
                <span className="d-block medium-text secondary-text mt-2">
                    <span className="__detail_name">Projetos: </span>12
                </span>
            </PageTitle>

            <div className="mb-4">
                <p className="page-section-title">Projetos</p>
                {/* <span className="small-text secondary-text mb-2 d-block">
                    Clique em um dos projetos para visualizar os detalhes
                </span> */}
                <span className="medium-text secondary-text mb-2 d-block">
                    Nenhum projeto encontrado
                </span>
                {/* <a href="/admin/projeto/1" className="full-width-box text-decoration-none">
                    <div>
                        <span>P001 - Projeto</span>
                    </div>
                    <span className="medium-text secondary-text d-flex align-items-center justify-content-between">
                        Status: em analise
                    </span>
                </a> */}
            </div>
            <div>
                <p className="page-section-title">Membros</p>
                <form onSubmit={addMemberToSector}>
                    <input
                        ref={inNewGroupMemberEmail}
                        name="email"
                        type="text"
                        className="default-input medium-input"
                        placeholder="Email do novo membro"
                    />
                    <LabeledButton type={"submit"} size="small">
                        Adicionar
                    </LabeledButton>
                </form>
                <Table className="default-table w-100 mt-4" borderless>
                    <thead>
                        <tr>
                            <th>Nome</th>
                            <th>Email</th>
                        </tr>
                    </thead>
                    <tbody>
                        {!sector.members.length && (
                            <tr>
                                <td className="td-primary">
                                    <div>Esse setor não possui participantes</div>
                                </td>
                                <td className="td-secondary d-flex align-items-center justify-content-between">
                                    <div></div>
                                </td>
                            </tr>
                        )}

                        {sector.members.map((representative) => (
                            <Representative
                                key={representative.id}
                                representative={representative}
                                setShowDeleteUserModal={setShowDeleteUserModal}
                                setRepresentativeToBeRemoved={setRepresentativeToBeRemoved}
                                canBeRemoved
                            />
                        ))}
                    </tbody>
                </Table>
            </div>
        </BasePage>
    );
};

export default UNICAPSectorPage;
