import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';

const API_URL = `${environment.apiUrl}`;

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  httpOptions: any;
  isLoggedIn: boolean;

  constructor(private httpClient: HttpClient) {
    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      'Access-Control-Allow-Origin': 'http://localhost:4200',
      'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,PATCH,OPTIONS'
    };
  }

  signIn(signInForm): Observable<any> {
    return this.httpClient.post(API_URL + 'auth/sign-in', {
      username: signInForm.username,
      password: signInForm.password
    }, this.httpOptions);
  }

  loginGoogle(token: string): Observable<any> {
    // tslint:disable-next-line:max-line-length
    // return this.httpClient.post( 'https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/api/public/auth/login-google&response_type=code' +
    //   '&client_id=531954897444-hsskaandnq4jd4688062gbludfanl04a.apps.googleusercontent.com&approval_prompt=force', this.httpOptions);
    return this.httpClient.get(API_URL + 'auth/login-google?token=' + token);
  }
}
