import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {TokenStorageService} from './token-storage.service';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(private router: Router, private tokenStorageService: TokenStorageService) {
  }
  // tslint:disable-next-line:max-line-length
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const currentUser = this.tokenStorageService.getUser();
    if (currentUser !== null) {
      const role = currentUser.roles[0];
      // if (route.data.roles.indexOf(role) === -1) {
      //   this.router.navigate(['/security/sign-in'], {queryParams: {returnUrl: state.url}}).then();
      //   Swal.fire('Thông báo !!', 'Bạn không có quyền truy cập vào trang này', 'warning').then();
      //   return false;
      // }
      return true;
    }
    this.router.navigate(['security/sign-in'], {queryParams: {returnUrl: state.url}}).then();
    return false;
  }
}
