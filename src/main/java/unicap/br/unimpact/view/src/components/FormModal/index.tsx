import React, { FormEvent } from "react";
import { Modal } from "react-bootstrap";
import LabeledButton from "../Buttons/LabeledButton";
import HighlightedLabel from "../HighlightedLabel";

interface props {
    title: string;
    titleClassName?: string;
    highlightedLabel?: string;
    submitButtonLabel?: string;
    show: boolean;
    setShow: (show: boolean) => void;
    onSubmit: (e: FormEvent<HTMLFormElement>) => void;
}

const FormModal: React.FC<props> = ({
    title,
    titleClassName = "",
    highlightedLabel,
    children,
    show,
    setShow,
    onSubmit,
    submitButtonLabel = "Enviar",
}) => {
    return (
        <Modal id="form-modal" show={show} centered onHide={() => setShow(false)}>
            <Modal.Header closeButton>
                <Modal.Title className={`main-text ${titleClassName}`}>{title}</Modal.Title>
            </Modal.Header>
            <form onSubmit={onSubmit}>
                <Modal.Body>
                    {highlightedLabel && <HighlightedLabel label={highlightedLabel} />}
                    {children}
                </Modal.Body>
                <Modal.Footer className="justify-content-center">
                    <LabeledButton type="submit" size="small">
                        {submitButtonLabel}
                    </LabeledButton>
                </Modal.Footer>
            </form>
        </Modal>
    );
};

export default FormModal;
