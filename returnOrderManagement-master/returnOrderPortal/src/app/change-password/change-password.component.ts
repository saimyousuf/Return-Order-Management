import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router"
import { LoginService } from '../services/login.service';
@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
   
       apiResponseForPasswordChange:string = "";
       
  constructor(private router:Router , private loginService:LoginService) { }

  ngOnInit(): void {

    if(!localStorage.getItem('token')){
      this.router.navigate(['/']);
    }
  }

  changePasswordForm(loginForm:any){
       
        this.loginService.changePassword(loginForm.value).subscribe((res:any)=>{
            
            this.apiResponseForPasswordChange = res.message
            
             loginForm.reset()

               
            
        })
      
  }

}
