import { TUserPhone,TUserAddress } from "./RegisterPhysicalPerson";

export const isValidName = (name: string): boolean => {
  if (name != "") {
    return true;
  }
  console.log("invalid name")
  return false;
};

export const isValidCorporateName = (corporateName: string): boolean => {
  if (corporateName != "") {
    return true;
  }
    return false;
};

export const isValidCPF = (cpf: any): boolean => {
  if (typeof cpf !== "string") return false;

  cpf = cpf.replace(/[^\d]+/g, "");

  if (cpf.length !== 11 || !!cpf.match(/(\d)\1{10}/)) return false;

  cpf = cpf.split("");

  const validator = cpf

    .filter(
      (digit: any, index: number, array: string | any[]) =>
        index >= array.length - 2 && digit
    )
    .map((el: string | number) => +el);

  const toValidate = (pop: number) =>
    cpf
      .filter(
        (digit: any, index: number, array: string | any[]) =>
          index < array.length - pop && digit
      )
      .map((el: string | number) => +el);

  const rest = (count: number, pop: number) =>
    ((toValidate(pop).reduce(
      (soma: number, el: number, i: number) => soma + el * (count - i),
      0
    ) *
      10) %
      11) %
    10;

  return !(rest(10, 2) !== validator[0] || rest(11, 1) !== validator[1]);
};

export const isValidCNPJ = (cnpj: string): boolean => {
  console.log(cnpj)
  cnpj = cnpj.replace(/[^\d]+/g, "");

  if (cnpj.length !== 14) {
    return false;
  }

  if (/^(\d)\1+$/.test(cnpj)) {
    return false;
  }

  let sum = 0;
  let factor = 5;
  for (let i = 0; i < 12; i++) {
    sum += parseInt(cnpj.charAt(i)) * factor;
    factor = factor === 2 ? 9 : factor - 1;
  }
  let digit = 11 - (sum % 11);
  if (digit > 9) {
    digit = 0;
  }

  if (parseInt(cnpj.charAt(12)) !== digit) {
    return false;
  }

  sum = 0;
  factor = 6;
  for (let i = 0; i < 13; i++) {
    sum += parseInt(cnpj.charAt(i)) * factor;
    factor = factor === 2 ? 9 : factor - 1;
  }
  digit = 11 - (sum % 11);
  if (digit > 9) {
    digit = 0;
  }

  if (parseInt(cnpj.charAt(13)) !== digit) {
    return false;
  }

  return true;
};

export const isValidRG = (rg: string): boolean => {
  rg = rg.replace(/[^\d]+/g, "");

  if (rg.length <= 30) {
    return true;
  }
  console.log("Invalid rg")
  return false;
};

export const isValidPhone = (phone: string): boolean => {
  if (phone.length == 11 || phone.length == 10) {
    return true;
  }
  console.log("phone = ",phone,"length =",phone.length)
  console.log("invalid phone")
  return false;
};

export const isValidPhones = (phones: TUserPhone[]): boolean => {
  const [phoneOne, phoneTwo] = phones;  
  if(isValidPhone(phoneOne.number) || isValidPhone(phoneTwo.number)){
    return true;
  }
  console.log("invalid phones")
  return false;
};

export const isValidCEP = (cep: string): boolean => {
  cep = cep.replace(/[^\d]+/g, "");
  if (cep.length == 8) {
    return true;
  }
  console.log("invalid cep")
  return false;
};

export const isValidAddress = (adress: TUserAddress[]): boolean => {
  let street = ""
  let number = ""
  let district = ""
  let city = ""
  let state = ""
  let cep = ""
  let country = ""
  
  for (const addressItem of adress) {
    street = addressItem.street;
    number = addressItem.number;
    district = addressItem.district;
    city = addressItem.city;
    state = addressItem.state;
    cep = addressItem.cep
    country = addressItem.country    
  }

  if(isValidStreet(street) && isValidHouseNumber(number) && isValidDistrict(district) && isValidCity(city) && isValidState(state)){
    return true
  }
  
  return false;
};

export const isValidStreet = (street: string): boolean => {
  if (street != "") {
    return true;
  }

  return false;
};

export const isValidHouseNumber = (houseNumber: string): boolean => {
  if (houseNumber != "") {
    return true;
  }
  return false;
};

export const isValidDistrict = (district: string): boolean => {
  if (district != "") {
    return true;
  }
  return false;
};

export const isValidCity = (city: string): boolean => {
  if (city != "") {
    return true;
  }
  return false;
};

export const isValidState = (state: string): boolean => {
  if (state != "") {
    return true;
  }
  return false;
};

export const isValidEmail = (email: string): boolean => {
  const emailValidationRegex = new RegExp(
    /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  );

  if (emailValidationRegex.test(email)) {
    return true;
  }
  console.log("invalid email")
  return false;
};

export const isValidPassword = (password: string): boolean => {
  const passwordValidationRegex = new RegExp(
    "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,30}$"
  );

  if (passwordValidationRegex.test(password)) {
    return true;
  }
  console.log("invalid password")
  return false;
};
