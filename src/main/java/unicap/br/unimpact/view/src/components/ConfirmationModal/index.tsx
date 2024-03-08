import React from "react";

import { Modal } from "react-bootstrap";
import LabeledButton from "../Buttons/LabeledButton";

interface props {
    title: string;
    content: string;
    onCancel: () => void;
    onConfirm: () => void;
    show?: boolean;
    isWarning?: boolean;
    invertButtons?: boolean;
    confirmationLabel?: string;
    cancelLabel?: string;
}

const ConfirmationModal: React.FC<props> = ({
    title,
    content,
    onCancel,
    onConfirm,
    show = false,
    isWarning = false,
    invertButtons = false,
    confirmationLabel = "CONFIRMAR",
    cancelLabel = "CANCELAR",
}) => {
    return (
        <Modal show={show} id="confirmation-modal">
            <Modal.Header>
                <h3 className={`${isWarning ? "modal-title-warning" : ""}`}>{title}</h3>
            </Modal.Header>
            <Modal.Body>
                <p>{content}</p>
                <div className="d-flex align-items-center justify-content-start">
                    <LabeledButton
                        onClick={invertButtons ? onConfirm : onCancel}
                        className="primary-button-small d-block me-4">
                        {invertButtons ? confirmationLabel : cancelLabel}
                    </LabeledButton>
                    <LabeledButton
                        onClick={invertButtons ? onCancel : onConfirm}
                        type="submit"
                        secondary
                        className="d-block secondary-button">
                        {invertButtons ? cancelLabel : confirmationLabel}
                    </LabeledButton>
                </div>
            </Modal.Body>
        </Modal>
    );
};

export default ConfirmationModal;
