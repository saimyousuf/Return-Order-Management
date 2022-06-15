import { Component, OnInit } from '@angular/core';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  usernameDontExist: string = '';
  error: string = '';
  show: boolean = false;

  constructor(private loginService: LoginService, private router: Router) {}

  ngOnInit(): void {

       if(localStorage.getItem("token")){
           this.router.navigate(['/dashboard']);
       }

  }

  password() {
    this.show = !this.show;
}

  usernameFound(event: any) {
    let username = event.target.value;
    this.loginService.usernameExist(username).subscribe((res) => {
      this.usernameDontExist = res.message;
    });
  }

  handleLogin(loginForm: any) {
    this.loginService.login(loginForm).subscribe((res) => {
      console.log(res);
      if (res.token) {
        localStorage.setItem('token', res.token);
        this.router.navigate(['/dashboard']).then(() => {
          window.location.reload();
        });
      } else {
        if (res.error) {
          this.error = res.message;
        }
      }
    });
  }
}
