import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../service/auth.service';
import {TokenStorageService} from '../../service/token-storage.service';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';
import {ShareService} from '../../service/share.service';
import {Title} from '@angular/platform-browser';
import {GoogleLoginProvider, SocialAuthService} from 'angularx-social-login';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {
  signInForm: FormGroup;
  roles: string[] = [];
  username: string;

  constructor(private toastrService: ToastrService,
              private authService: AuthService,
              private socialAuthService: SocialAuthService,
              private tokenStorageService: TokenStorageService,
              private router: Router,
              private shareService: ShareService,
              private title: Title) {
    this.title.setTitle('Đăng nhập');
  }

  ngOnInit(): void {
    this.signInForm = new FormGroup({
        username: new FormControl('', [Validators.maxLength(30), Validators.required]),
        password: new FormControl('', [Validators.maxLength(30), Validators.required]),
        rememberMe: new FormControl()
      }
    );
    if (this.tokenStorageService.getToken()) {
      this.authService.isLoggedIn = true;
      this.roles = this.tokenStorageService.getUser().roles;
      this.username = this.tokenStorageService.getUser().username;
    }
  }

  submit() {
    this.authService.signIn(this.signInForm.value).subscribe(jwtResponse => {
      if (jwtResponse.status === 202) {
        this.toastrService.error('Tài khoản hoặc mật khẩu không đúng', 'Đăng nhập không thành công', {
          extendedTimeOut: 1500,
          timeOut: 3000
        });
        this.authService.isLoggedIn = false;
      } else {
        if (this.signInForm.value.rememberMe) {
          this.tokenStorageService.saveTokenLocal(jwtResponse.token);
          this.tokenStorageService.saveUserLocal(jwtResponse);
        } else {
          this.tokenStorageService.saveTokenSession(jwtResponse.token);
          this.tokenStorageService.saveUserSession(jwtResponse);
        }
        this.authService.isLoggedIn = true;
        this.router.navigateByUrl('cart').then();
        this.toastrService.success('Chào ' + this.tokenStorageService.getUser().name, 'Đăng nhập thành công', {
          extendedTimeOut: 1500,
          timeOut: 3000
        });
        this.shareService.sendClickEvent();
      }
    });
  }

  loginGoogle() {
    this.socialAuthService.signIn(GoogleLoginProvider.PROVIDER_ID).then(jwtResponse => {
      console.log(jwtResponse.idToken);
      // this.tokenStorageService.saveTokenSession(jwtResponse.token);
      // this.tokenStorageService.saveUserSession(jwtResponse);
      // this.authService.isLoggedIn = true;
      // this.router.navigateByUrl('cart').then();
      // this.toastrService.success('Chào ' + this.tokenStorageService.getUser().email, 'Đăng nhập thành công', {
      //   extendedTimeOut: 1500,
      //   timeOut: 3000
      // });
      // this.shareService.sendClickEvent();
    });
  }
}
