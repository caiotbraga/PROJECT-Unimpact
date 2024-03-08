import React, { useRef } from "react";

import FormFloatingInput from "../../components/FormsFields/FormInput";

import { Button, Modal } from "react-bootstrap";
import LabeledButton from "../../components/Buttons/LabeledButton";

interface props {
    onConfirm: () => void;
    onCancel: () => void;
    show: boolean;
}

const DeleteAccountconfirmationModal: React.FC<props> = ({ onConfirm, show, onCancel }) => {
    const inPassword = useRef<HTMLInputElement | null>(null);
    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        if (inPassword.current?.value.trim() === "") {
            inPassword.current?.classList.add("is-invalid");
            return;
        }

        onConfirm();
    };

    return (
        <Modal show={show}>
            <Modal.Header>
                <h4>Deletar contar</h4>
            </Modal.Header>
            <form onSubmit={handleSubmit}>
                <Modal.Body>
                    <p>Você tem certeza que deseja remover sua conta? Essa ação não por ser desfeita</p>
                    <FormFloatingInput
                        fRef={inPassword}
                        id={"password"}
                        label={"Insira sua senha"}
                        inputSize="fill"
                        fieldType="password"
                    />
                </Modal.Body>
                <Modal.Footer>
                    <LabeledButton onClick={onCancel} className="d-block mx-auto">
                        Cancelar
                    </LabeledButton>
                    <LabeledButton type={"submit"} className="d-block mx-auto" secondary>
                        Confirmar
                    </LabeledButton>
                </Modal.Footer>
            </form>
        </Modal>
    );
};

export default DeleteAccountconfirmationModal;
