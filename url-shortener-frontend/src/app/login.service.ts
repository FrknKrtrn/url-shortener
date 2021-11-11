import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AppUser } from './app-user';
import { LoginRequest } from './login-request';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  authenticate(request: LoginRequest) {
    let headers = new HttpHeaders({'Content-Type': 'application/json'});
    let authorizationData = 'Basic ' + btoa(request.email + ':' + request.password);
    headers.append('Authorization', authorizationData);
    
    return this.http.post<AppUser>(`${this.baseUrl}/login`, request, {headers}).pipe(
     map(
       userData => {
        sessionStorage.setItem('email', request.email);
        let authString = 'Basic ' + btoa(request.email + ':' + request.password);
        sessionStorage.setItem('basicauth', authString);

        return userData;
       }
     )

    );
  }

  isUserLoggedIn() {
    let appUser = sessionStorage.getItem('email')
    console.log(!(appUser === null))
    
    return !(appUser === null)
  }

  logOut() {
    sessionStorage.removeItem('email')
  }

}


/* previous login, it works 
export class LoginService {

  private baseUrl = environment.baseUrl;
  
  constructor(private http: HttpClient) { }

  public login(username: string, password: string) {

    const headers = new HttpHeaders({Authorization: 'Basic ' + btoa(username + ':' + password)});

    return this.http.get(`${this.baseUrl}/`, {headers, responseType: 'text' as 'json'});
  }

}
*/
