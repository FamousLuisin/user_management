import type { Address } from "./addressType";
import type { Email } from "./emailType";
import type { Phone } from "./phoneType";

export type ClientResponse = {
    id: number;
    name: string;
    cpf: string;
    address: Address;
    emails: Email[];
    phones: Phone[];
}

export type ClientRequest = {
    name: string;
    cpf: string;
    cep: string;
    emails: Email[];
    phones: Phone[];
}

export type ClienteUpdate = {
    name: string,
    cpf: string
}