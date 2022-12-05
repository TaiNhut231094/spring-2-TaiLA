import { Injectable } from '@angular/core';

const TOKEN_KEY = 'Auth-token';
const USER_KEY = 'Auth-user';
@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  roles: [];
  constructor() { }

  signOut(): void {
    window.localStorage.clear();
    window.sessionStorage.clear();
  }

  // public saveLocalStorage(loginResponse) {
  //   window.localStorage.removeItem('Auth-token');
  //   window.localStorage.setItem('Auth-token', loginResponse.token);
  //   window.localStorage.setItem('Roles', JSON.stringify(loginResponse.roles));
  //   window.localStorage.setItem('Username', loginResponse.name);
  // }
  //
  // public saveSessionStorage(loginResponse) {
  //   window.sessionStorage.removeItem('Auth-token');
  //   window.sessionStorage.setItem('Auth-token', loginResponse.token);
  //   window.sessionStorage.setItem('Roles', JSON.stringify(loginResponse.roles));
  //   window.sessionStorage.setItem('Username', loginResponse.name);
  // }
  //
  // public getToken(): string {
  //   if (window.localStorage.getItem('Auth-token') != null) {
  //     return localStorage.getItem('Auth-token');
  //   } else {
  //     return sessionStorage.getItem('Auth-token');
  //   }
  // }
  //
  // public getUsername(): string {
  //   if (window.localStorage.getItem('Username') != null) {
  //     return localStorage.getItem('Username');
  //   } else {
  //     return sessionStorage.getItem('Username');
  //   }
  // }
  //
  // public getRoles(): string [] {
  //   if (this.getToken()) {
  //    JSON.parse(localStorage.getItem('Roles')).forEach(role => {
  //      // @ts-ignore
  //      this.roles.push(role.authority);
  //    });
  //   }
  //   return this.roles;
  // }

  public saveTokenLocal(token: string): void {
    window.localStorage.removeItem(TOKEN_KEY);
    window.localStorage.setItem(TOKEN_KEY, token);
  }

  public saveTokenSession(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string {
    if (window.localStorage.getItem(TOKEN_KEY) !== null) {
      return localStorage.getItem(TOKEN_KEY);
    } else {
      return sessionStorage.getItem(TOKEN_KEY);
    }
  }

  public saveUserLocal(user): void {
    window.localStorage.removeItem(USER_KEY);
    window.localStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public saveUserSession(user): void {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser() {
    if (window.localStorage.getItem(USER_KEY) !== null) {
      return JSON.parse(localStorage.getItem(USER_KEY));
    } else {
      return JSON.parse(sessionStorage.getItem(USER_KEY));
    }
  }

  public getRoles(): string [] {
    if (this.getToken()) {
     JSON.parse(localStorage.getItem('Roles')).forEach(role => {
       // @ts-ignore
       this.roles.push(role.authority);
     });
    }
    return this.roles;
  }
}
