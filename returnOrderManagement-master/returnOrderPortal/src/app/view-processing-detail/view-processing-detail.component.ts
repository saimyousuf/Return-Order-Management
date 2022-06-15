import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router"
import {ComponentProcessingService} from "../services/component-processing.service"
@Component({
  selector: 'app-view-processing-detail',
  templateUrl: './view-processing-detail.component.html',
  styleUrls: ['./view-processing-detail.component.css']
})
export class ViewProcessingDetailComponent implements OnInit {
     
     processingDetails : any = []
    
  constructor(private router:Router , private componentProcessingService: ComponentProcessingService) { }

  ngOnInit(): void {
    if(!localStorage.getItem('token')){
      this.router.navigate(['/']);
    }else{
      this.getProcessingResponseDetails()
    }
     
  }

  getProcessingResponseDetails(){
    this.componentProcessingService.getProcessingDetails().subscribe(response=>{
        this.processingDetails = response
   
    })
  }

}
