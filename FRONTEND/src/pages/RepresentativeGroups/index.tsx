import React, { useContext, useEffect, useRef, useState } from "react";

import BasePage from "../../components/BasePage";
import AddNewGroupModal from "../../components/AddNewGroupModal";
import Group from "./Group";
import GroupModal from "../../components/GroupModal";
import PageTitle from "../../components/PageTitle";

import { Button } from "react-bootstrap";

import axios from "axios";
import { getRequestBasicConfig } from "../../utils";
import { AlertsContext } from "../../App";
import { fetchGroupsByOwner, fetchGroupsByParticipate } from "../../services/GroupServices";
import { TGroup } from "../../types/GroupTypes";

const RepresentativeGroupsPage: React.FC = () => {
    const { addToast } = useContext(AlertsContext);

    const [groups, setGroups] = useState<TGroup[]>([]);
    const [showAddNewGroupModal, setShowAddNewGroupModal] = useState(false);

    const [openedGroup, setOpenedGroup] = useState<TGroup | null>(null);
    const [showGroupDetailsModal, setShowGroupDetailsModal] = useState<boolean>(false);

    const currentUserEmail = useRef("");

    const fetchGroups = () => {
        const handleError = () => {
            addToast({
                header: "Erro!",
                content: "Ocorreu um erro ao carregar seus grupos.",
                isSuccess: false,
            });
            setGroups([]);
        };
        
        fetchGroupsByOwner(({ data }) => {
            let groupsAux = data.object;
            fetchGroupsByParticipate(({ data }) => {
                groupsAux = [...groupsAux, ...data.object];
                setGroups(groupsAux);
            }, handleError);
        }, handleError);
    };

    const getUserEmail = () => {
        axios.get(`http://localhost:9090/person/physical/`, getRequestBasicConfig()).then(({ data }) => {
            currentUserEmail.current = data.object.email;
        });
    };

    useEffect(() => {
        fetchGroups();
        getUserEmail();
    }, []);

    return (
        <BasePage>
            {openedGroup && (
                <GroupModal
                    show={showGroupDetailsModal}
                    setShow={setShowGroupDetailsModal}
                    group={openedGroup}
                    setOpenedGroup={setOpenedGroup}
                    isCurrentUserGroupOwner={currentUserEmail.current === openedGroup.ownerUserEmail}
                    createdByPhysicalPerson={currentUserEmail.current === openedGroup.ownerUserEmail}
                    updateGroupList={fetchGroups}
                />
            )}
            <AddNewGroupModal
                show={showAddNewGroupModal}
                setShow={setShowAddNewGroupModal}
                fetchGroups={fetchGroups}
                createdByPhysicalPerson
            />
            <PageTitle title="Grupos" />
            {/* <p className="medium-text mb-4">Grupos que você participa em "UNICAP"</p> */}
            <Button
                onClick={() => setShowAddNewGroupModal(true)}
                type="submit"
                className="primary-button-small mb-4">
                CRIAR GRUPO
            </Button>
            <div id="__grupos">
                {!groups.length && <span className="medium-text">Você não participa de nenhum grupo.</span>}
                {groups.map((group) => (
                    <Group
                        key={group.id}
                        group={group}
                        setOpenedGroup={(group) => {
                            setOpenedGroup(group);
                            setShowGroupDetailsModal(true);
                        }}
                    />
                ))}
            </div>
        </BasePage>
    );
};

export default RepresentativeGroupsPage;
function AxiosResponse<T, U>() {
    throw new Error("Function not implemented.");
}
