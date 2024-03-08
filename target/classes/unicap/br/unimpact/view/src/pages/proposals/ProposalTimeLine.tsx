import React from 'react'
import BasePage from "../../components/BasePage";
import { useParams, Link } from "react-router-dom";

const ProposalTimeLine: React.FC = () => {

  const { proposalId } = useParams();
  return (
    <BasePage id="proposal-details">
        <h1>{`ProposalId: ${proposalId}`}</h1>
    </BasePage>
  )
}

export default ProposalTimeLine