import React, { FormEvent, useContext, useEffect, useRef, useState } from "react";

import { Button, Table } from "react-bootstrap";
import { AlertsContext } from "../../App";
import ConfirmationModal from "../ConfirmationModal";
import { getRequestBasicConfig } from "../../utils";

import { TPhysicalUser } from "../../types/UserTypes";

import axios from "axios";
import Representative from "../Representative";
import LabeledButton from "../Buttons/LabeledButton";

interface props {
    fetchRepresentativesEndpoint?: string;
    removeRepresentativeEndpoint?: string;
}

const Representatives: React.FC<props> = ({
    fetchRepresentativesEndpoint = "http://localhost:9090/person/juridical/representatives",
    removeRepresentativeEndpoint = "http://localhost:9090/person/juridical/representatives",
}) => {
    const { addToast } = useContext(AlertsContext);

    const inNewRepresentative = useRef<HTMLInputElement | null>(null);

    const [representatives, setRepresentatives] = useState<TPhysicalUser[]>([]);
    const [updateTable, setUpdateTable] = useState(true);

    const [showDeleteUserModal, setShowDeleteUserModal] = useState(false);
    const [representativeToBeRemoved, setRepresentativeToBeRemoved] = useState<string | null>(null);

    const onCancelRepresentativeRemoval = () => {
        setShowDeleteUserModal(false);
        setRepresentativeToBeRemoved(null);
    };

    const onConfirmRepresentativeRemoval = () => {
        setShowDeleteUserModal(false);
        removeRepresentative();
        setRepresentativeToBeRemoved(null);
    };

    const fetchRepresentatives = () => {
        axios
            .get(fetchRepresentativesEndpoint, getRequestBasicConfig())
            .then(({ data }) => {
                setRepresentatives(data.object);
            })
            .catch((error) => {
                if (error.response.status === 400) setRepresentatives([]);
            });
    };

    const removeRepresentative = () => {
        if (!representativeToBeRemoved) return;

        axios
            .delete(
                `${removeRepresentativeEndpoint}?representativeEmail=${representativeToBeRemoved}`,
                getRequestBasicConfig()
            )
            .then(() => {
                fetchRepresentatives();
                addToast({
                    header: "Removido com sucesso!",
                    content: "O representante foi removido.",
                    isSuccess: true,
                });
            })
            .catch((error) => {
                console.log(error.response);
            });
    };

    const addRepresentative = (e: FormEvent) => {
        e.preventDefault();
        if (!inNewRepresentative.current || inNewRepresentative.current.value.trim() === "") return;

        const representativeEmail = inNewRepresentative.current.value;
        axios
            .post(
                `http://localhost:9090/notification/invite?notificationType=INVITE_REPRESENTATIVE&recipientsEmail=${representativeEmail}`,
                {},
                getRequestBasicConfig()
            )
            .then(() => {
                addToast({
                    header: "Solicitação enviada!",
                    content: "Foi enviada uma solicitação para o remetente se tornar representante da instituição.",
                    isSuccess: true,
                });
            })
            .catch((error) => {
                if (error.response.status === 404)
                    addToast({
                        header: "Erro!",
                        content: "O e-mail fornecido não pertence à um usuário do UNIMPACT.",
                        isSuccess: false,
                    });
            });
    };

    useEffect(() => {
        if (updateTable) {
            fetchRepresentatives();
            setUpdateTable(false);
        }
    }, [setUpdateTable, updateTable]);

    return (
        <>
            <ConfirmationModal
                title={"Remover representante?"}
                content={"Deseja realmente remove o representante? Essa ação não pode ser desfeita."}
                show={showDeleteUserModal}
                onCancel={onCancelRepresentativeRemoval}
                onConfirm={onConfirmRepresentativeRemoval}
                invertButtons
                isWarning
            />
            <Table id="representatives-table" className="default-table w-100 medium-table-size" borderless>
                <thead>
                    <tr>
                        <th>Nome</th>
                        <th>Email</th>
                    </tr>
                </thead>
                <tbody>
                    {!representatives.length && (
                        <tr>
                            <td className="td-primary">
                                <div>Não há representantes na organização</div>
                            </td>
                            <td className="td-secondary d-flex align-items-center justify-content-between">
                                <div></div>
                            </td>
                        </tr>
                    )}
                    {representatives.map((representative) => (
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
            <form id="add-representative-form" onSubmit={addRepresentative}>
                <input
                    ref={inNewRepresentative}
                    name="email"
                    type="text"
                    className="default-input medium-input"
                    placeholder="Email do novo representante"
                />
                <LabeledButton type="submit" size="small">
                    ADICIONAR
                </LabeledButton>
            </form>
        </>
    );
};

export default Representatives;
