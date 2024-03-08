import React from "react";
import { Link } from "react-router-dom";
import PageTitle from "../../components/PageTitle";

import HighlightedLabel from "../../components/HighlightedLabel";

import {ReactComponent as PhysicalPersonIcon} from '../../resources/icons/pessoa.svg';
import {ReactComponent as JuridicalPersonIcon} from '../../resources/icons/pessoa_juridica.svg';

const RegisterTypeChoose: React.FC = () => {
    return (
        <>
            <PageTitle title="Cadastro" />
            <HighlightedLabel label="Selecione o tipo de cadastro" />
            <div className="register-options w-100 h-100 row align-items-center justify-content-center">
                <Link to="/cadastro/pessoa-fisica" className="register-choose col-md-auto">
                    <PhysicalPersonIcon />
                    <span>Pessoa física</span>
                </Link>
                <Link to="/cadastro/pessoa-juridica" className="register-choose col-md-auto">
                    <JuridicalPersonIcon />
                    <span>Pessoa jurídica</span>
                </Link>
            </div>
        </>
    );
};

export default RegisterTypeChoose;
