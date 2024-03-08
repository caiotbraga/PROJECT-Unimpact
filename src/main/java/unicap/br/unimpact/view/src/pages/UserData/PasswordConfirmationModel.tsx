import React from "react";

import FormFloatingInput from "../../components/FormsFields/FormInput";

import { Button, Modal } from "react-bootstrap";
import LabeledButton from "../../components/Buttons/LabeledButton";

interface props {
    onConfirm: () => void;
    onCancel: () => void;
    show: boolean;
}

const PasswordConfirmationModal: React.FC<props> = ({ onConfirm, show, onCancel }) => {
    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        onConfirm();
    };

    return (
        <Modal show={show}>
            <Modal.Header>Confirme sua senha</Modal.Header>
            <form onSubmit={handleSubmit}>
                <Modal.Body>
                    <FormFloatingInput id={"password"} label={"Insira sua senha"} inputSize="fill" fieldType="password" />
                </Modal.Body>
                <Modal.Footer>
                    <LabeledButton onClick={onCancel} className="mx-auto d-block" secondary>
                        Cancelar
                    </LabeledButton>
                    <LabeledButton type={"submit"} className="mx-auto d-block">
                        Confirmar
                    </LabeledButton>
                </Modal.Footer>
            </form>
        </Modal>
    );
};

export default PasswordConfirmationModal;
