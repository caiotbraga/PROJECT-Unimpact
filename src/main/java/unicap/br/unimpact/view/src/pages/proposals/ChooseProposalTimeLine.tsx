import React, { useEffect, useState } from "react";

import { useParams, Link } from "react-router-dom";
import { fetchProposal } from "../../services/ProposalServices";
import { TProposal } from "../../types/ProposalTypes";
import BasePage from "../../components/BasePage";
import PageTitle from "../../components/PageTitle";
import HighlightedLabel from "../../components/HighlightedLabel";

import { ReactComponent as ProjectIcon } from "../../resources/icons/ProjectIcon.svg";
import { ReactComponent as ProposalIcon } from "../../resources/icons/ProposalIcon.svg";
import { ReactComponent as SolicitationIcon } from "../../resources/icons/SolicitationIcon.svg";

const ChooseProposalTimeLine: React.FC = () => {
  const [proposal, setProposal] = useState<TProposal | null>(null);

  const { proposalId } = useParams();

  useEffect(() => {
    fetchProposal(
      ({ data }) => {
        setProposal(data.object);
      },
      Number(proposalId),
      (e) => {
        console.log(e.response);
      }
    );
  }, [proposalId]);

  if (!proposal) return <PageTitle title={"Carregando..."} />;

  console.log(proposal);
  return (
    <BasePage id="proposal-details">
      <div>
        <PageTitle title={proposal.metadataProject.title}>
          <div className="proposal-metadata">
            <span>
              <span className="medium-text">Código: </span>
              {proposal.id}
            </span>
            <span>
              <span className="medium-text">Estado: </span>
              {proposal.actualState.state}
            </span>
            <span>
              <span className="medium-text">Autor(a): </span>
              {proposal.responsible.physicalPersonResponsible.name}
            </span>
          </div>
          <div className="proposal-metadata">
            <span>
              <span className="medium-text">Status: </span>
              {proposal.actualState.actualStatus.status}
            </span>
            <span>
              <span className="medium-text">Responsável: </span>
              {proposal.actualState.actualStatus.responsible
                ? proposal.actualState.actualStatus.responsible
                : "Não Atribuído"}
            </span>
            <span>
              <span className="medium-text">Criado em: </span>
              {new Date(proposal.actualState.createdAt).toLocaleDateString()}
            </span>
          </div>
        </PageTitle>
      </div>
      <HighlightedLabel label="Clique em um dos estados do projeto para visualizar os detalhes" />
      <div className="register-options w-100 h-100 row align-items-center justify-content-center">
        <Link
          to={`/voce/history/${proposalId}`}
          className="register-choose col-md-auto"
        >
          <SolicitationIcon />
          <span>Solicitação</span>
          {console.log(`ProposalID: ${proposalId}`)}
        </Link>
        <Link
          to="/cadastro/pessoa-juridica"
          className="register-choose col-md-auto"
        >
          <ProposalIcon />
          <span>Proposta</span>
        </Link>
        <Link
          to="/cadastro/pessoa-juridica"
          className="register-choose col-md-auto"
        >
          <ProjectIcon />
          <span>Projeto</span>
        </Link>
      </div>
    </BasePage>
  );
};

export default ChooseProposalTimeLine;
