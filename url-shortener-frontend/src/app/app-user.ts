import { Url } from "./url";

export class AppUser {
    id!: number;
    firstName!: string;
    lastName!: string;
    email!: string;
    password!: string;
    role!: string;
    urls!: Url[];
}
