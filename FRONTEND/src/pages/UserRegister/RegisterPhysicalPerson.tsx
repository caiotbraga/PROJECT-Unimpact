import React, { useRef, useState, useEffect } from "react";

import axios from "axios";
import {
  blurRG,
  blurCEP,
  blurCPF,
  blurEmail,
  blurPassword,
  blurPasswordConfirmation,
  onlyNumbers,
  phoneMask,
  getValueOfField,
  returnWithOnlyNumbers,
} from "./EventHandles";

import HighlightedLabel from "../../components/HighlightedLabel";

import FormFloatingInput from "../../components/FormsFields/FormInput";
import PageTitle from "../../components/PageTitle";
import LabeledButton from "../../components/Buttons/LabeledButton";
import { Check } from "@material-ui/icons";
import { Exception } from "sass";
import {
  isValidCPF,
  isValidRG,
  isValidName,
  isValidEmail,
  isValidPassword,
  isValidPhones,
  isValidAddress,
} from "./FieldValidation";
import { TAddress } from "../../types/UserTypes";

export interface TPhysicalPersonUser {
  addresses: TUserAddress[];
  cpf: string;
  email: string;
  name: string;
  password: string;
  phones: TUserPhone[];
  rg: string;
}

export interface TUserAddress {
  cep: string;
  country: string;
  district: string;
  number: string;
  state: string;
  street: string;
  city: string;
}

export interface TUserPhone {
  number: string;
}

const RegisterPhysicalPerson: React.FC = () => {
  const [screenWidth, setScreenWidth] = useState<number>(window.innerWidth);

  const handleWindowResize = () => {
    setScreenWidth(window.innerWidth);
  };

  useEffect(() => {
    window.addEventListener("resize", handleWindowResize);

    return () => {
      window.removeEventListener("resize", handleWindowResize);
    };
  }, []);

  const inName = useRef<HTMLInputElement | null>(null);
  const inCPF = useRef<HTMLInputElement | null>(null);
  const inRG = useRef<HTMLInputElement | null>(null);
  const inPhoneOne = useRef<HTMLInputElement | null>(null);
  const inPhoneTwo = useRef<HTMLInputElement | null>(null);
  const inCEP = useRef<HTMLInputElement | null>(null);
  const inStreet = useRef<HTMLInputElement | null>(null);
  const inCity = useRef<HTMLInputElement | null>(null);
  const inState = useRef<HTMLInputElement | null>(null);
  const inDistrict = useRef<HTMLInputElement | null>(null);
  const inHouseNumber = useRef<HTMLInputElement | null>(null);
  const inHouseNumberComplement = useRef<HTMLInputElement | null>(null);
  const inEmail = useRef<HTMLInputElement | null>(null);
  const inPassword = useRef<HTMLInputElement | null>(null);
  const inPasswordConfirmation = useRef<HTMLInputElement | null>(null);
  const boxTermsAndConditions = useRef<HTMLInputElement | null>(null);

  const validateNewPhysicalPersonForm = (newUser: TPhysicalPersonUser) => {
    if (
      boxTermsAndConditions.current?.checked &&
      isValidName(newUser.name) &&
      isValidCPF(newUser.cpf) &&
      isValidRG(newUser.rg) &&
      isValidPhones(newUser.phones) &&
      isValidAddress(newUser.addresses) &&
      isValidEmail(newUser.email) &&
      isValidPassword(newUser.password)
    ) {
      console.log("valid user");
      console.log(newUser.rg);
      return true;
    }
    console.log("invalid user");
    return false;
  };

  const handlePostNewPhysicalPerson = (newUser: TPhysicalPersonUser) => {
    
    console.log(newUser)
    axios
      .post("http://localhost:9090/auth/registerPhysicalPerson", { ...newUser })
      .then((response) => {
        console.log(response);
        if (response.status >= 200 && response.status <= 299)
          window.location.href = "/entrar";
      })
      .catch((exception) => {
        console.log(exception.response);
      });
  };

  const constructNewPhysicalPersonUser = (): TPhysicalPersonUser => {
    const address: TAddress = {
      cep: getValueOfField(returnWithOnlyNumbers(inCEP)),
      country: "Brasil",
      district: getValueOfField(inDistrict),
      number: getValueOfField(inHouseNumber),
      city: getValueOfField(inCity),
      state: getValueOfField(inState),
      street: getValueOfField(inStreet),
    };

    return {
      addresses: [address],
      cpf: getValueOfField(returnWithOnlyNumbers(inCPF)),
      email: getValueOfField(inEmail),
      name: getValueOfField(inName),
      password: getValueOfField(inPassword),
      phones: [
        { number: getValueOfField(returnWithOnlyNumbers(inPhoneOne)) },
        { number: getValueOfField(returnWithOnlyNumbers(inPhoneTwo)) },
      ],
      rg: getValueOfField(returnWithOnlyNumbers(inRG)),
    };
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const newUser = constructNewPhysicalPersonUser();
    console.log(newUser);
    if (validateNewPhysicalPersonForm(newUser)) {
      handlePostNewPhysicalPerson(newUser);
    }
  };

  const handleBlur = (e: React.FocusEvent<HTMLInputElement>) => {
    switch (e.target.id) {
      case "cpf":
        blurCPF(inCPF);
        break;
      case "rg":
        blurRG(inRG);
        break;
      case "cep":
        blurCEP(inCEP, inStreet, inDistrict, inCity, inState);
        break;
      case "email":
        blurEmail(inEmail);
        break;
      case "password":
        blurPassword(inPassword);
        break;
      case "passwordConfirmation":
        blurPasswordConfirmation(inPasswordConfirmation, inPassword);
        break;

      default:
        break;
    }
  };

  const handleOnChange = (e: React.FormEvent) => {
    const targetId = (e.target as HTMLDivElement).id;

    switch (targetId) {
      case "cpf":
        onlyNumbers(inCPF);
        break;
      case "rg":
        onlyNumbers(inRG);
        break;
      case "cep":
        onlyNumbers(inCEP);
        break;
      case "phoneOne":
        phoneMask(inPhoneOne);
        break;
      case "phoneTwo":
        phoneMask(inPhoneTwo);
        break;

      default:
        break;
    }
  };

  return (
    <>
      <PageTitle title="Cadastro - Pessoa física" />
      <HighlightedLabel label="Preencha atentamente os campos abaixo" />
      <form
        id="register-physical-person-form"
        className="user-register-form"
        onSubmit={handleSubmit}
      >
        {screenWidth}

        {/* start first line */}

        <div className="__form_container_half">
          <div className="__form_row">
            <FormFloatingInput
              fRef={inName}
              id={"fullName"}
              label={"Nome completo"}
              inputSize="large"
              required
              invalidFeedback="Digite seu nome."
            />
          </div>
          <div className="__form_container_quarter">
            <div
              className="__form_row"
              onBlur={handleBlur}
              onChange={handleOnChange}
            >
              <FormFloatingInput
                fRef={inCPF}
                id={"cpf"}
                label={"CPF"}
                placeholder="Somente números"
                inputSize="medium"
                required
                invalidFeedback="Digite um CPF válido."
                disableAutoComplete={true}
              />
            </div>
            <div
              className="__form_row"
              onBlur={handleBlur}
              onChange={handleOnChange}
            >
              <FormFloatingInput
                fRef={inRG}
                id={"rg"}
                label={"RG"}
                placeholder="Somente números"
                inputSize="medium"
                required
                invalidFeedback="Digite um RG válido."
                disableAutoComplete={true}
              />
            </div>
          </div>
        </div>
        {/* end first line */}

        {/* start second line */}
        <div className="__form_container_half">
          <div className="__form_container_quarter">
            <div className="__form_row" onChange={handleOnChange}>
              <FormFloatingInput
                fRef={inPhoneOne}
                id={"phoneOne"}
                label={"Telefone 1"}
                invalidFeedback="Somente números"
                required
              />
            </div>

            <div className="__form_row" onChange={handleOnChange}>
              <FormFloatingInput
                fRef={inPhoneTwo}
                id={"phoneTwo"}
                label={"Telefone 2"}
                invalidFeedback="Somente números"
              />
            </div>
          </div>
          <div className="__form_container_half"></div>
        </div>
        {/* end second line */}

        {/* start subsection line*/}
        <span className="subsection-title mb-4"></span>
        {/* end subsection line*/}

        {/* start third line*/}
        <div
          className="__form_row"
          onBlur={handleBlur}
          onChange={handleOnChange}
        >
          <FormFloatingInput
            fRef={inCEP}
            id={"cep"}
            label={"CEP"}
            placeholder="Somente números"
            invalidFeedback="Insira um CEP válido."
            disableAutoComplete={true}
          />
        </div>
        {/* end third line*/}

        {/* start fourth line*/}
        <div className="__form_container_half">
          <div className="__form_container_half">
            <div className="__form_row">
              <FormFloatingInput
                fRef={inStreet}
                id={"street"}
                label={"Rua"}
                invalidFeedback="Rua"
                inputSize="large"
                required
              />
            </div>
            <div className="__form_container_quarter"></div>
          </div>
          <div className="__form_container_quarter">
            <div className="__form_row">
              <FormFloatingInput
                fRef={inHouseNumber}
                id={"houseNumber"}
                label={"Número"}
                invalidFeedback="Digite o número da casa"
                required
              />
            </div>
            <div className="__form_row">
              <FormFloatingInput
                fRef={inHouseNumberComplement}
                id={"addressComplement"}
                label={"Complemento"}
              />
            </div>
          </div>
        </div>
        {/* end fourth line*/}

        {/* start fifth line*/}
        <div className="__form_container_half">
          <div className="__form_container_quarter">
            <div className="__form_row">
              <FormFloatingInput
                fRef={inDistrict}
                id={"disctrict"}
                label={"Bairro"}
                invalidFeedback="Digite o bairro"
                required
              />
            </div>
            <div className="__form_row">
              <FormFloatingInput
                fRef={inCity}
                id={"city"}
                label={"Cidade"}
                invalidFeedback="Digite a cidade"
                required
              />
            </div>
          </div>
          <div className="__form_container_quarter">
            <div className="__form_row">
              <FormFloatingInput
                fRef={inState}
                id={"state"}
                label={"Estado"}
                invalidFeedback="Digite o estado"
                required
              />
            </div>
            <div className="__form_container_half"></div>
          </div>
        </div>
        {/* start sixth line*/}

        {/* start subsection*/}
        <span className="subsection-title mb-4">INFORMAÇÕES DE LOGIN</span>
        {/* end subsection*/}

        {/*start sixth line*/}
        <div className="__form_row" onBlur={handleBlur}>
          <FormFloatingInput
            fRef={inEmail}
            id={"email"}
            label={"E-mail"}
            inputSize="large"
            required
            invalidFeedback="Digite um e-mail válido"
          />
        </div>
        {/* end sixth line*/}

        {/* start seventh line*/}
        <div className="__form_container_half">
          <div className="__form_container_quarter">
            <div className="__form_row" onBlur={handleBlur}>
              <FormFloatingInput
                fRef={inPassword}
                id={"password"}
                label={"Senha"}
                required
                placeholder=""
                invalidFeedback="Digite uma senha válida ( 8 à 30 digitos contendo ao menos uma letra maiuscula, uma letra minuscula, um caracter especial e um numero ). "
                fieldType="password"
              />
            </div>

            <div className="__form_row" onBlur={handleBlur}>
              <FormFloatingInput
                fRef={inPasswordConfirmation}
                id={"passwordConfirmation"}
                label={"Repita a senha"}
                required
                invalidFeedback="As senhas não condizem."
                fieldType="password"
              />
            </div>
          </div>
          <div className="__form_container_half"></div>
        </div>
        {/* end seventh line*/}

        {/* start checkbox section*/}
        <div className="form-check" style={{ marginTop: "64px" }}>
          <input
            ref={boxTermsAndConditions}
            className="form-check-input"
            type="checkbox"
            value=""
            id="flexCheckDefault"
          />
          <label
            className="form-check-label required"
            htmlFor="flexCheckDefault"
            style={{ fontSize: "14px" }}
          >
            Li e concordo com os termos
          </label>
        </div>
        {/* end checkbox section*/}

        {/*start send button}*/}
        <LabeledButton type={"submit"} className="mx-auto d-block mt-5">
          Enviar
        </LabeledButton>
        {/*end send button}*/}
      </form>
    </>
  );
};

export default RegisterPhysicalPerson;
