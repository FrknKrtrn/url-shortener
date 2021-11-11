import { AppUser } from "./app-user";

export class Url {
    id!: number;
    originalUrl!: string;
    shortenedUrl!: string;
    appUser!: AppUser;
}
