import axios from "axios";
import {
  isValidCPF,
  isValidRG,
  isValidEmail,
  isValidPassword,
  isValidCNPJ,
} from "./FieldValidation";
import React from "react";
import { forEachChild } from "typescript";

export const clearField = (field: React.RefObject<HTMLInputElement>) => {
  field.current!.value = "";
};

export const setIsInvalid = (field: React.RefObject<HTMLInputElement>) => {
  field.current?.classList.add("is-invalid");
};

export const setIsValid = (field: React.RefObject<HTMLInputElement>) => {
  field.current?.classList.remove("is-invalid");
};

export const setValueOfField = (
  field: React.RefObject<HTMLInputElement>,
  string: string
) => {
  field.current!.value = string;
};

export const getValueOfField = (
  field: React.RefObject<HTMLInputElement>
): string => {
  return field.current?.value || "";
};

export const applieRegexToField = (
  field: React.RefObject<HTMLInputElement>,
  regex: RegExp,
  string2: string
) => {
  field.current!.value = field.current!.value.replace(regex, string2);
};

export const fieldLength = (
  field: React.RefObject<HTMLInputElement>
): number => {
  return field.current?.value.length || 0;
};

export const blurRG = (inRG: React.RefObject<HTMLInputElement>) => {
  if (isValidRG(getValueOfField(inRG))) {
    setIsValid(inRG);
    return;
  }
  setIsInvalid(inRG);
  clearField(inRG);
  return;
};

export const blurCEP = async (
  inCEP: React.RefObject<HTMLInputElement>,
  inStreet: React.RefObject<HTMLInputElement>,
  inDistrict: React.RefObject<HTMLInputElement>,
  inCity: React.RefObject<HTMLInputElement>,
  inState: React.RefObject<HTMLInputElement>
) => {
  const url = `https://viacep.com.br/ws/${inCEP.current!.value}/json/`;

  try {
    const response = await axios.get(url);
    const { logradouro, bairro, localidade, uf } = response.data;
    setValueOfField(inStreet, logradouro);
    setValueOfField(inDistrict, bairro);
    setValueOfField(inCity, localidade);
    setValueOfField(inState, uf);
    applieRegexToField(inCEP, /^(\d{5})(\d{3})?$/, "$1-$2");
    setIsValid(inCEP);
  } catch (exception) {
    if (axios.isAxiosError(exception)) {
      setIsInvalid(inCEP);
      clearField(inCEP);
      console.log("Error in axios.get(), invalid CEP. \n" + exception);
    } else {
      throw exception;
    }
  }
};

export const blurCPF = (inCPF: React.RefObject<HTMLInputElement>) => {
  if (isValidCPF(getValueOfField(inCPF))) {
    setIsValid(inCPF);
    applieRegexToField(inCPF, /^(\d{3})(\d{3})(\d{3})(\d{2})$/, "$1.$2.$3-$4");
    return;
  }
  setIsInvalid(inCPF);
  clearField(inCPF);
  return;
};

export const blurCNPJ = (inCNPJ: React.RefObject<HTMLInputElement>) => {
  if (isValidCNPJ(getValueOfField(inCNPJ))) {
    setIsValid(inCNPJ);
    applieRegexToField(
      inCNPJ,
      /^(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})$/,
      "$1.$2.$3/$4-$5"
    );
    return;
  }
  setIsInvalid(inCNPJ);
  clearField(inCNPJ);
  return;
};

export const blurEmail = (inEmail: React.RefObject<HTMLInputElement>) => {
  if (isValidEmail(getValueOfField(inEmail))) {
    setIsValid(inEmail);
    return;
  }
  setIsInvalid(inEmail);
  clearField(inEmail);
  return;
};

export const blurPassword = (inPassword: React.RefObject<HTMLInputElement>) => {
  if (isValidPassword(getValueOfField(inPassword))) {
    setIsValid(inPassword);
    return;
  }
  setIsInvalid(inPassword);
  clearField(inPassword);
  return;
};

export const blurPasswordConfirmation = (
  inPasswordConfirmation: React.RefObject<HTMLInputElement>,
  inPassword: React.RefObject<HTMLInputElement>
) => {
  if (getValueOfField(inPassword) == getValueOfField(inPasswordConfirmation)) {
    setIsValid(inPasswordConfirmation);
    return;
  }
  setIsInvalid(inPasswordConfirmation);
  clearField(inPasswordConfirmation);
  return;
};

export const onlyNumbers = (inRef: React.RefObject<HTMLInputElement>) => {
  inRef.current!.value = inRef.current!.value.replace(/\D/g, "");
};

export const returnWithOnlyNumbers = (inRef: React.RefObject<HTMLInputElement>): React.RefObject<HTMLInputElement> => {
  inRef.current!.value = inRef.current!.value.replace(/\D/g, "");
  return inRef;
};



export const phoneMask = (inRef: React.RefObject<HTMLInputElement>) => {
  const maxLength = 15;

  let formattedValue = inRef
    .current!.value.replace(/\D/g, "")
    .replace(/(\d{2})(\d)/, "($1) $2")
    .replace(/(\d)(\d{4})$/, "$1-$2");

    
  if (formattedValue.length > maxLength) {
    formattedValue = formattedValue.slice(0, -1);
    formattedValue = formattedValue.replace(/\D/g, "")
    .replace(/(\d{2})(\d)/, "($1) $2")
    .replace(/(\d)(\d{4})$/, "$1-$2");
  }
  

  setValueOfField(inRef, formattedValue);
};
