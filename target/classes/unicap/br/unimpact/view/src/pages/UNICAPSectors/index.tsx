import React, { useContext, useEffect, useState } from "react";

import AddNewGroupModal from "../../components/AddNewGroupModal";
import BasePage from "../../components/BasePage";
import BoxedItem from "../../components/BoxedItem";
import LabeledButton from "../../components/Buttons/LabeledButton";
import PageTitle from "../../components/PageTitle";

import { AlertsContext } from "../../App";

import { fetchUNICAPGroups } from "../../services/GroupServices";
import { TGroup } from "../../types/GroupTypes";

const UNICAPSectorsPage: React.FC = () => {
    const { addToast } = useContext(AlertsContext);

    const [showNewGroupModal, setShowNewGroupModal] = useState(false);
    const [groups, setGroups] = useState<TGroup[]>([]);

    const fetchGroups = () => {
        fetchUNICAPGroups(
            ({ data }) => {
                setGroups(data.object);
            },
            () => {
                addToast({
                    header: "Erro!",
                    content: "Ocorreu um erro ao carregar seus grupos.",
                    isSuccess: false,
                });
                setGroups([]);
            }
        );
    };

    useEffect(() => {
        fetchGroups();
    }, []);

    return (
        <BasePage>
            <PageTitle title="Setores" />
            <p className="medium-text">
                Os setores são responsáveis por analisar demandas, planejar soluções e executar projetos.
            </p>
            <AddNewGroupModal
                show={showNewGroupModal}
                setShow={setShowNewGroupModal}
                fetchGroups={fetchGroups}
                title={"Criar setor"}
            />
            <LabeledButton type={"submit"} className="mb-4" size="small" onClick={() => setShowNewGroupModal(true)}>
                Criar setor
            </LabeledButton>
            {!groups.length && <span className="d-block mt-4">Nenhum setor encontrado</span>}
            {groups.map((group) => (
                <BoxedItem key={group.id} href={`/admin/setor/${group.id}`} title={group.name} />
            ))}
        </BasePage>
    );
};

export default UNICAPSectorsPage;
