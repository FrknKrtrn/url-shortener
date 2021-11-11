import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AppUser } from './app-user';

@Injectable({
  providedIn: 'root'
})
export class SignUpService {

  private baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  public signUpUser(user: AppUser): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/sign-up`, user);
  }
}
