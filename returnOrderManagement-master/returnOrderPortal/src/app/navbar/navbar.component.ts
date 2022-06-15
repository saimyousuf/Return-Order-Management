import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router"
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
       
        token : boolean = false
      
  constructor(private router: Router) { }

  ngOnInit(): void {
        
       if(localStorage.getItem('token')){
        this.token = true
       }

  }

  logout(){
    
      if(window.confirm("Are you sure ?")){
         localStorage.removeItem("token")
         this.token = false
         this.router.navigate(['/']).then(()=>{
          window.location.reload()
         })
      }
     
  }

}
