import React, { useContext, useEffect, useState } from "react";

import { Button, Table } from "react-bootstrap";
import { AlertsContext } from "../../App";

import AddNewGroupModal from "../../components/AddNewGroupModal";
import Group from "./Group";
import GroupModal from "../../components/GroupModal";
import { fetchGroupsByOwner } from "../../services/GroupServices";
import { TGroup } from "../../types/GroupTypes";

const Groups: React.FC = () => {
    const { addToast } = useContext(AlertsContext);

    const [groups, setGroups] = useState<TGroup[]>([]);
    const [showAddNewGroupModal, setShowAddNewGroupModal] = useState(false);

    const [openedGroup, setOpenedGroup] = useState<TGroup | null>(null);
    const [showGroupDetailsModal, setShowGroupDetailsModal] = useState<boolean>(false);

    const fetchGroups = () => {
        fetchGroupsByOwner(
            ({ data }) => {
                setGroups(data.object);
            },
            ({ response }) => {
                if (response.status !== 400) {
                    addToast({
                        header: "Erro!",
                        content: "Ocorreu um erro ao carregar seus grupos.",
                        isSuccess: false,
                    });
                }
                setGroups([]);
            }
        );
    };

    useEffect(() => {
        fetchGroups();
    }, []);

    useEffect(() => {
        if (!showGroupDetailsModal) fetchGroups();
    }, [showGroupDetailsModal]);

    return (
        <>
            <AddNewGroupModal show={showAddNewGroupModal} setShow={setShowAddNewGroupModal} fetchGroups={fetchGroups} />
            {openedGroup && (
                <GroupModal
                    show={showGroupDetailsModal}
                    setShow={setShowGroupDetailsModal}
                    group={openedGroup}
                    setOpenedGroup={setOpenedGroup}
                    isCurrentUserGroupOwner={true}
                    updateGroupList={fetchGroups}
                />
            )}
            <Table id="representatives-table" className="default-table medium-table-size" borderless>
                <thead>
                    <tr>
                        <th>Nome</th>
                        <th>Participantes</th>
                    </tr>
                </thead>
                <tbody>
                    {!groups.length && (
                        <tr>
                            <td className="td-primary">
                                <div>Não há grupos na organização</div>
                            </td>
                            <td className="td-secondary d-flex align-items-center justify-content-between">
                                <div></div>
                            </td>
                        </tr>
                    )}
                    {groups.map((group) => (
                        <Group
                            key={group.id}
                            group={group}
                            setOpenedGroup={(group: TGroup) => {
                                setOpenedGroup(group);
                                setShowGroupDetailsModal(true);
                            }}
                        />
                    ))}
                </tbody>
            </Table>
            <Button
                type="submit"
                className="primary-button-small"
                onClick={() => setShowAddNewGroupModal(true)}>
                CRIAR GRUPO
            </Button>
        </>
    );
};

export default Groups;
