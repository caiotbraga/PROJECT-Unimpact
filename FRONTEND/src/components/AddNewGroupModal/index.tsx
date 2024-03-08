import axios from "axios";
import React, { FormEvent, useContext, useEffect, useRef } from "react";

import { Button, Modal } from "react-bootstrap";

import DefaultInput from "../FormsFields/DefaultInput";
import { getRequestBasicConfig } from "../../utils";
import { TNewGroup } from "../../types/GroupTypes";
import { createGroup } from "../../services/GroupServices";
import { AlertsContext } from "../../App";
import LabeledButton from "../Buttons/LabeledButton";

interface props {
    show: boolean;
    setShow: (show: boolean) => void;
    title?: string;
    fetchGroups: () => void;
    createdByPhysicalPerson?: boolean;
}

const AddNewGroupModal: React.FC<props> = ({
    show,
    setShow,
    fetchGroups,
    createdByPhysicalPerson,
    title = "Criar novo grupo",
}) => {
    const { addToast } = useContext(AlertsContext);

    const inName = useRef<HTMLInputElement | null>(null);
    const inDescription = useRef<HTMLInputElement | null>(null);
    const inAreasOfOperation = useRef<HTMLInputElement | null>(null);

    const currentUserEmail = useRef("");

    const validateNewGroupObject = (newGroup: TNewGroup) => {
        let isValid = true;
        if (newGroup.name.trim() === "") {
            inName.current?.classList.add("is-invalid");
            isValid = false;
        } else if (inName.current?.classList.contains("is-invalid")) inName.current?.classList.remove("is-invalid");

        return isValid;
    };

    const buildNewGroupObject = (): TNewGroup => {
        return {
            areasOfOperation: inAreasOfOperation.current?.value.split(",") || null,
            description: inDescription.current?.value || null,
            name: inName.current?.value || "",
            ownerUserEmail: currentUserEmail.current,
        };
    };

    const handleSubmit = (e: FormEvent) => {
        e.preventDefault();

        const newGroup = buildNewGroupObject();
        if (validateNewGroupObject(newGroup)) {
            createGroup(
                newGroup,
                () => {
                    fetchGroups();
                    setShow(false);
                },
                (error: any) => {
                    console.log(error.response);
                    addToast({
                        header: "Erro!",
                        content: "Ocorreu um erro ao criar o grupo. Tente novamente mais tarde.",
                        isSuccess: false,
                    });
                }
            );
        }
    };

    const handleCancel = () => {
        if (inName.current) inName.current.value = "";
        if (inDescription.current) inDescription.current.value = "";
        if (inAreasOfOperation.current) inAreasOfOperation.current.value = "";

        setShow(false);
    };

    const getUserEmail = () => {
        axios
            .get(
                `http://localhost:9090/person/${createdByPhysicalPerson ? "physical" : "juridical"}/`,
                getRequestBasicConfig()
            )
            .then(({ data }) => {
                currentUserEmail.current = data.object.email;
            })
            .catch(() => {
                currentUserEmail.current = "admin.unimpact@unicap.br";
            });
    };

    useEffect(() => {
        getUserEmail();
    }, []);

    return (
        <Modal show={show}>
            <Modal.Header>
                <Modal.Title className={`modal-title-small`}>{title}</Modal.Title>
            </Modal.Header>
            <form onSubmit={handleSubmit}>
                <Modal.Body>
                    <DefaultInput fRef={inName} id={"groupName"} label={"Nome"} placeholder="Ex: RH" />
                    <DefaultInput fRef={inDescription} id={"description"} label={"Breve descrição"} />
                    <DefaultInput
                        fRef={inAreasOfOperation}
                        id={"araesOfOperation"}
                        label={"Áreas de operação"}
                        placeholder="Ex: Recursos humanos, Financeiro"
                    />
                </Modal.Body>
                <div className="w-100 d-flex align-items-center pe-3 pb-3 ps-3">
                <LabeledButton type="submit" className="d-block me-4" size="small">
                    Criar
                </LabeledButton>
                <LabeledButton className="d-block" size="small" secondary onClick={handleCancel}>
                    Cancelar
                </LabeledButton>
                </div>
            </form>
        </Modal>
    );
};

export default AddNewGroupModal;
