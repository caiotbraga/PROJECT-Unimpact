import React, { FormEvent, useEffect, useRef, useState } from "react";

import FormFloatingInput from "../../components/FormsFields/FormInput";
import HighlightedLabel from "../../components/HighlightedLabel";
import FormSelect from "../../components/FormsFields/FormSelect";

import axios from "axios";
import { TAddress, TPostJuridicalUser } from "../../types/UserTypes";
import PageTitle from "../../components/PageTitle";
import LabeledButton from "../../components/Buttons/LabeledButton";
import {
  blurCEP,
  blurCNPJ,
  blurCPF,
  blurEmail,
  blurPassword,
  blurPasswordConfirmation,
  getValueOfField,
  onlyNumbers,
  phoneMask,
} from "./EventHandles";

const RegisterJuridicalPerson: React.FC = () => {
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

  const slctInstitutionType = useRef<HTMLSelectElement | null>(null);

  const inCorporateName = useRef<HTMLInputElement | null>(null);
  const inFantasyName = useRef<HTMLInputElement | null>(null);
  const inCNPJ = useRef<HTMLInputElement | null>(null);
  const inPhoneOne = useRef<HTMLInputElement | null>(null);
  const inPhoneTwo = useRef<HTMLInputElement | null>(null);

  const inCEP = useRef<HTMLInputElement | null>(null);
  const inStreet = useRef<HTMLInputElement | null>(null);
  const inState = useRef<HTMLInputElement | null>(null);
  const inDistrict = useRef<HTMLInputElement | null>(null);
  const inCity = useRef<HTMLInputElement | null>(null);
  const inHouseNumber = useRef<HTMLInputElement | null>(null);
  const inHouseNumberComplement = useRef<HTMLInputElement | null>(null);

  const inEmail = useRef<HTMLInputElement | null>(null);
  const inPassword = useRef<HTMLInputElement | null>(null);
  const inPasswordConfirmation = useRef<HTMLInputElement | null>(null);

  const boxTermsAndConditions = useRef<HTMLInputElement | null>(null);

  const [institutionTypes, setInstitutionTypes] = useState<string[]>([]);

  const buildJuridicalPersonObject = (): TPostJuridicalUser => {
    const address: TAddress = {
      cep: getValueOfField(inCEP),
      country: "Brasil",
      district: getValueOfField(inDistrict),
      number: getValueOfField(inPhoneOne),
      city: "city",
      state: getValueOfField(inState),
      street: getValueOfField(inStreet),
    };

    return {
      addresses: [address],
      cnpj: getValueOfField(inCNPJ),
      fantasyName: getValueOfField(inFantasyName),
      email: getValueOfField(inEmail),
      institutionType: slctInstitutionType.current?.value.toUpperCase() || "",
      name: getValueOfField(inCorporateName),
      phones: [
        {
          number: getValueOfField(inPhoneOne),
        },
      ],
      password: getValueOfField(inPassword),
    };
  };

  const validateNewJuridicalPersonForm = (
    juridicalUser: TPostJuridicalUser
  ): boolean => {
    let isValid = true;

    if (juridicalUser.institutionType.toLowerCase() === "none") {
      slctInstitutionType.current?.classList.add("is-invalid");
      if (isValid) isValid = false;
    } else if (slctInstitutionType.current?.classList.contains("is-invalid"))
      slctInstitutionType.current?.classList.remove("is-invalid");

    if (juridicalUser.name.trim() === "") {
      inCorporateName.current?.classList.add("is-invalid");
      if (isValid) isValid = false;
    } else if (inCorporateName.current?.classList.contains("is-invalid"))
      inCorporateName.current?.classList.remove("is-invalid");

    if (juridicalUser.cnpj.trim() === "") {
      inCNPJ.current?.classList.add("is-invalid");
      if (isValid) isValid = false;
    } else if (inCNPJ.current?.classList.contains("is-invalid"))
      inCNPJ.current?.classList.remove("is-invalid");

    if (juridicalUser.phones[0].number.trim() === "") {
      inPhoneOne.current?.classList.add("is-invalid");
      if (isValid) isValid = false;
    } else if (inPhoneOne.current?.classList.contains("is-invalid"))
      inPhoneOne.current?.classList.remove("is-invalid");

    if (juridicalUser.addresses[0].cep.trim() === "") {
      inCEP.current?.classList.add("is-invalid");
      if (isValid) isValid = false;
    } else if (inCEP.current?.classList.contains("is-invalid"))
      inCEP.current?.classList.remove("is-invalid");

    if (juridicalUser.email.trim() === "") {
      inEmail.current?.classList.add("is-invalid");
      if (isValid) isValid = false;
    } else if (inEmail.current?.classList.contains("is-invalid"))
      inEmail.current?.classList.remove("is-invalid");

    if (juridicalUser.password.trim() === "") {
      inPassword.current?.classList.add("is-invalid");
      if (isValid) isValid = false;
    } else if (inPassword.current?.classList.contains("is-invalid"))
      inPassword.current?.classList.remove("is-invalid");

    return isValid;
  };

  const handlePostNewJuridicalPerson = (juridicalUser: TPostJuridicalUser) => {
    axios
      .post("http://localhost:9090/auth/registerJuridicalPerson", {
        ...juridicalUser,
      })
      .then((response) => {
        if (response.status >= 200 && response.status <= 299)
          window.location.href = "/entrar";
      })
      .catch((exception) => {
        console.log(exception.response);
      });
  };

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    const juridicalUser = buildJuridicalPersonObject();

    if (validateNewJuridicalPersonForm(juridicalUser)) {
      handlePostNewJuridicalPerson(juridicalUser);
    }
  };

  const fetchInstitutionTypes = () => {
    axios
      .get("http://localhost:9090/auxiliary/institution/type")
      .then((resp) => {
        setInstitutionTypes(resp.data.object);
      })
      .catch((error) => {
        console.log(error.response);
      });
  };

  useEffect(() => {
    fetchInstitutionTypes();
  }, []);

  const handleBlur = (e: React.FocusEvent<HTMLInputElement>) => {
    switch (e.target.id) {
      case "cnpj":
        blurCNPJ(inCNPJ);
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
      case "cnpj":
        onlyNumbers(inCNPJ);
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
      <PageTitle title="Cadastro - Pessoa jurídica" />
      <HighlightedLabel label="Preencha atentamente os campos abaixo" />
      <form
        id="register-physical-person-form"
        className="user-register-form"
        onSubmit={handleSubmit}
      >
        <span className="subsection-title mb-4">
          Selecione o tipo da instituição
        </span>
        <div className="__form_row">
          <FormSelect
            fRef={slctInstitutionType}
            label="Tipo de instituição"
            options={institutionTypes}
            id={"civilStateSelect"}
            required
          />
        </div>
        <span className="subsection-title mb-4">
          Informações sobre a instituição
        </span>

        <div className="__form_container_half">
          <div className="__form_row">
            <FormFloatingInput
              fRef={inCorporateName}
              id={"corporateName"}
              label={"Razão social"}
              inputSize={"large"}
              required
            />
          </div>
          <div
            className="__form_row"
            onBlur={handleBlur}
            onChange={handleOnChange}
          >
            <FormFloatingInput
              fRef={inCNPJ}
              id={"cnpj"}
              label={"CNPJ"}
              placeholder="Somente números"
              required
            />
          </div>
        </div>

        <div className="__form_row">
          <FormFloatingInput
            fRef={inFantasyName}
            id={"fantasyName"}
            inputSize={"large"}
            label={"Nome fantasia"}
          />
        </div>
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
        <span className="subsection-title mb-4">Informações de endereço</span>

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
        <span className="subsection-title mb-4">Informações de login</span>

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
        <div className="form-check">
          <input
            ref={boxTermsAndConditions}
            className="form-check-input"
            type="checkbox"
            id="inBoxTermsAndConditions"
          />
          <label
            className="form-check-label required"
            htmlFor="inBoxTermsAndConditions"
            style={{ fontSize: "14px" }}
          >
            Li e concordo com os termos
          </label>
        </div>
        <LabeledButton type={"submit"} className="mx-auto d-block mt-5">
          Enviar
        </LabeledButton>
      </form>
    </>
  );
};

export default RegisterJuridicalPerson;
