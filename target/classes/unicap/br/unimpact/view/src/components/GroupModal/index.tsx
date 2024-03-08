import React, { useContext, useEffect, useRef, useState } from "react";

import ConfirmationModal from "../ConfirmationModal";
import Representative from "../Representative";

import { Button, Modal, Table } from "react-bootstrap";

import axios from "axios";
import { AlertsContext } from "../../App";
import { getRequestBasicConfig } from "../../utils";
import { TJuridicalUser, TPhysicalUser } from "../../types/UserTypes";

import { deleteGroupById, fetchGroupById, removeGroupMember } from "../../services/GroupServices";
import { TGroup } from "../../types/GroupTypes";

interface props {
    show: boolean;
    setShow: (show: boolean) => void;
    group: TGroup;
    setOpenedGroup: (group: TGroup | null) => void;
    isCurrentUserGroupOwner?: boolean;
    createdByPhysicalPerson?: boolean;
    updateGroupList?: () => void;
}

const GroupModal: React.FC<props> = ({
    group,
    show,
    setShow,
    setOpenedGroup,
    isCurrentUserGroupOwner,
    createdByPhysicalPerson,
    updateGroupList,
}) => {
    const { addToast } = useContext(AlertsContext);

    const [showRemoveMemberModal, setShowRemoveMemberModal] = useState(false);
    const [memberEmailToBeRemoved, setMemberEmailToBeRemoved] = useState<string | null>(null);
    const [groupOwner, setGroupOwner] = useState<TPhysicalUser | TJuridicalUser | null>(null);
    const [currentPhysicalUser, setCurrentPhysicalUser] = useState<TPhysicalUser | TJuridicalUser | null>(null);
    const [showDeleteGroupModal, setShowDeleteGroupModal] = useState(false);

    const inNewGroupMemberEmail = useRef<HTMLInputElement | null>(null);
    const groupToBeRemoved = useRef<number | null>(null);

    const onCancelRepresentativeRemoval = () => {
        setShowRemoveMemberModal(false);
        setMemberEmailToBeRemoved(null);
    };

    const onConfirmRepresentativeRemoval = () => {
        setShowRemoveMemberModal(false);
        removeMember();
        setMemberEmailToBeRemoved(null);
    };

    const onCancelGroupRemoval = () => {
        groupToBeRemoved.current = null;
        setShowDeleteGroupModal(false);
        setShow(true);
    };

    const onConfirmGroupRemoval = () => {
        setShowDeleteGroupModal(false);
        removeGroup();
        groupToBeRemoved.current = null;
        setShow(false);
    };

    const updateOpenedGroup = () => {
        fetchGroupById(
            group.id,
            ({ data }) => {
                setOpenedGroup(data.object);
            },
            ({ response }) => {
                console.log(response);
                addToast({
                    header: "Erro!",
                    content: "Ocorreu um erro ao tentar atualizar os dados do grupo.",
                });
            }
        );
    };

    const removeMember = () => {
        const memberBaseInfo = {
            groupId: group.id,
            memberEmail: memberEmailToBeRemoved,
        };
        removeGroupMember(
            memberBaseInfo,
            () => {
                addToast({
                    header: "Sucesso!",
                    content: isCurrentUserGroupOwner ? "Membro removido com sucesso." : "Você saiu do grupo.",
                    isSuccess: true,
                });
                updateOpenedGroup();
                if (!isCurrentUserGroupOwner) {
                    setShow(false);
                    if (updateGroupList) updateGroupList();
                }
            },
            () => {
                addToast({
                    header: "Erro!",
                    content: "Ocorreu um erro tentar remover o membro do grupo.",
                    isSuccess: false,
                });
            }
        );
    };

    const addMember = () => {
        if (!inNewGroupMemberEmail.current) return;

        if (inNewGroupMemberEmail.current.value.trim() === "") {
            inNewGroupMemberEmail.current.classList.add("is-invalid");
            return;
        }

        axios
            .post(
                `http://localhost:9090/notification/invite?groupId=${group.id}&notificationType=INVITE_GROUP&recipientsEmail=${inNewGroupMemberEmail.current.value}`,
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
            .finally(() => {
                updateOpenedGroup();
            });
    };

    const removeGroup = () => {
        deleteGroupById(
            group.id,
            () => {
                addToast({
                    header: "Sucesso!",
                    content: "Grupo removido com sucesso!",
                    isSuccess: true,
                });
                setShow(false);
                if (updateGroupList) updateGroupList();
            },
            ({ response }) => {
                console.log(response);
                addToast({
                    header: "Erro!",
                    content: "Ocorreu um erro tentar remover o grupo.",
                    isSuccess: false,
                });
            }
        );
    };

    const handleRemoveOrExitGroup = () => {
        if (isCurrentUserGroupOwner) {
            groupToBeRemoved.current = group.id;
            setShowDeleteGroupModal(true);
            setShow(false);
        } else if (currentPhysicalUser) {
            setMemberEmailToBeRemoved(currentPhysicalUser.email);
            setShowRemoveMemberModal(true);
        }
    };

    const getGroupOwnerData = () => {
        axios
            .get(`http://localhost:9090/auth/user?email=${group.ownerUserEmail}`, getRequestBasicConfig())
            .then(({ data }) => {
                setGroupOwner(data.object);
            });
    };

    const getCurrentPhysicalUserData = () => {
        axios.get(`http://localhost:9090/person/physical/`, getRequestBasicConfig()).then(({ data }) => {
            setCurrentPhysicalUser(data.object);
        });
    };

    useEffect(() => {
        if (show) {
            updateOpenedGroup();
            getGroupOwnerData();
            if (!createdByPhysicalPerson) getCurrentPhysicalUserData();
        }
    }, [show]);

    return (
        <>
            <ConfirmationModal
                title={isCurrentUserGroupOwner ? "Remover membro do grupo?" : "Deseja sair do grupo?"}
                content={
                    isCurrentUserGroupOwner
                        ? "Deseja realmente remove o membro deste grupo?"
                        : "Se você sair do grupo só poderá voltar através de um convite do proprietário."
                }
                show={showRemoveMemberModal}
                onCancel={onCancelRepresentativeRemoval}
                onConfirm={onConfirmRepresentativeRemoval}
                invertButtons
                isWarning
            />
            <ConfirmationModal
                title={"Excluir grupo?"}
                content={"Deseja realmente excluir esse grupo? Todos os participantes serão automaticamente removidos."}
                show={showDeleteGroupModal}
                onCancel={onCancelGroupRemoval}
                onConfirm={onConfirmGroupRemoval}
                invertButtons
                isWarning
            />
            <Modal
                show={show}
                id="group-modal"
                onHide={() => setShow(false)}
                centered
                className={`${showRemoveMemberModal && "fade-out-modal"}`}>
                <Modal.Header closeButton>
                    <Modal.Title className="main-text">{group.name}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <p id="group_description" className="medium-text m-0">
                        {!group.description ? "Descrição não fornecida." : group.description}
                    </p>
                    <div id="__projects">
                        <span className="subsection-title">Projetos</span>
                        <span className="medium-text">Não há projetos pertencentes a este grupo</span>
                        {/* <ul>
                            <li>P023 - Aula de computação para idosos</li>
                            <li>P320 - Capacitação para professores</li>
                        </ul> */}
                    </div>
                    <div id="__members">
                        {isCurrentUserGroupOwner && (
                            <>
                                <span className="subsection-title">Adicione um novo membro</span>
                                <input
                                    ref={inNewGroupMemberEmail}
                                    id="newGroupMemberEmail"
                                    type="text"
                                    className="default-input medium-input"
                                    placeholder="E-mail"
                                />
                                <Button
                                    onClick={addMember}
                                    type="submit"
                                    className="primary-button-small">
                                    ADICIONAR
                                </Button>
                            </>
                        )}
                        <span className="subsection-title">Membros</span>
                        <Table id="members-table" className="default-table fill-element w-100" borderless>
                            <thead>
                                <tr>
                                    <th>Nome</th>
                                    <th>E-mail</th>
                                </tr>
                            </thead>
                            <tbody>
                                {groupOwner && (
                                    <Representative
                                        key={groupOwner.id}
                                        representative={groupOwner}
                                        setShowDeleteUserModal={setShowRemoveMemberModal}
                                        setRepresentativeToBeRemoved={setMemberEmailToBeRemoved}
                                        canBeRemoved
                                        isOwner={true}
                                    />
                                )}
                                {group.members.map((representative) => (
                                    <Representative
                                        key={representative.id}
                                        representative={representative}
                                        setShowDeleteUserModal={setShowRemoveMemberModal}
                                        setRepresentativeToBeRemoved={setMemberEmailToBeRemoved}
                                    />
                                ))}
                            </tbody>
                        </Table>
                        <button onClick={handleRemoveOrExitGroup}>
                            <span className="small-text text-danger text-decoration-underline">
                                {isCurrentUserGroupOwner ? "Excluir grupo" : "Sair do grupo"}
                            </span>
                        </button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    );
};

export default GroupModal;
