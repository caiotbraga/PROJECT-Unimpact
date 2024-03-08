import React, { FormEvent, useState } from "react";
import { useParams } from "react-router-dom";
import LabeledButton from "../../../components/Buttons/LabeledButton";
import FormModal from "../../../components/FormModal";
import ProposalDetails from "../../../components/ProposalDetails";
import postNewProposal from "../../proposals/NewProposal";

const MiddleUserProposalManagementPage = () => {
    const params = useParams();
    if (!params.proposalId) return null;

    return (
        <>
            <ProposalDetails proposalId={Number(params.proposalId)}>
                <AcceptReject />
            </ProposalDetails>
        </>
    );
};

const AcceptReject = () => {
    const [showRejectProposal, setShowRejectProposal] = useState(false);
    const [showAdjustProposal, setShowAdjustProposal] = useState(false);

    const handleRejectProposal = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setShowRejectProposal(false);

        // Substitua o alert pelo uso de postNewProposal
        alert ("oi")
        postNewProposal( 
        );
    };

    const handleAdjustProposal = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setShowAdjustProposal(false);

        // Substitua o alert pelo uso de postNewProposal
        alert ("oi")
        postNewProposal( 
        );
    };

    return (
        <>
            <FormModal
                title={"Recusar solicitação"}
                titleClassName="text-danger"
                highlightedLabel="Descreva o motivo da recusa."
                show={showRejectProposal}
                setShow={setShowRejectProposal}
                onSubmit={handleRejectProposal}
            >
                <textarea
                    className={`fill-input no-outline-focus modal-default-textarea`}
                    placeholder="De maneira clara e objetiva, informe ao solicitante o motivo da recusa."
                />
            </FormModal>

            <FormModal
                title={"Solicitar ajustes"}
                highlightedLabel="Descreva os ajustes necessários para a solicitação"
                show={showAdjustProposal}
                setShow={setShowAdjustProposal}
                onSubmit={handleAdjustProposal}
            >
                <textarea
                    className={`fill-input no-outline-focus modal-default-textarea`}
                    placeholder="Ex: incluir mais pessoas a serem beneficiadas."
                />
            </FormModal>

            <div className="d-flex align-items-center justify-content-center mt-5 pt-5">
                <LabeledButton>Aceitar</LabeledButton>
                <LabeledButton secondary className="ms-4 me-4" onClick={() => setShowAdjustProposal(true)}>
                    Solicitar ajustes
                </LabeledButton>
                <LabeledButton className="danger-btn no-outline-focus" onClick={() => setShowRejectProposal(true)}>
                    recusar
                </LabeledButton>
            </div>
        </>
    );
};

export default MiddleUserProposalManagementPage;
