import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoginService } from './login.service';
import { Url } from './url';
import { UrlDto } from './url-dto';

@Injectable({
  providedIn: 'root'
})
export class UrlService {

  private baseUrl = environment.baseUrl;

  constructor(private http: HttpClient, private loginService: LoginService) { }

  public getUrls(): Observable<Url[]> {    
    return this.http.get<Url[]>(`${this.baseUrl}/urls`);
  }

  public generateUrl(urlDto: UrlDto, userId: number): Observable<Url> {
    return this.http.post<Url>(`${this.baseUrl}/users/${userId}/urls`, urlDto);
  }

  public getUrlsByUserId(userId: number): Observable<Url[]> {
    return this.http.get<Url[]>(`${this.baseUrl}/users/${userId}/urls`);
  }

  public deleteUrl(urlId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${urlId}`);
  }
}
