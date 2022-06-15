import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {ComponentProcessingService} from  "../services/component-processing.service"
@Component({
  selector: 'app-provide-details-for-return-order',
  templateUrl: './provide-details-for-return-order.component.html',
  styleUrls: ['./provide-details-for-return-order.component.css'],
})
export class ProvideDetailsForReturnOrderComponent implements OnInit {

  constructor(private router: Router , private componentProcessingService : ComponentProcessingService) {}

  ngOnInit(): void {
    if (!localStorage.getItem('token')) {
      this.router.navigate(['/']);
    }
  }

  handleReturnOrderDetailsForm(returnOrderDetailsForm: any) {
    let formData = returnOrderDetailsForm.value;

    let priorityRequest = formData.isPriorityRequest === 'true' ? true : false;

    formData.isPriorityRequest = priorityRequest;
    formData.contactNumber = parseInt(formData.contactNumber);
    formData.creditCardNumber = parseInt(formData.creditCardNumber);
    formData.quantity = parseInt(formData.quantity);
     
      this.componentProcessingService.processDetail(formData).subscribe(response=>{
           if(response.requestId){
            window.alert(`Return Order Initiated.Return Id - ${response.requestId}`);
            returnOrderDetailsForm.reset();
           }else{
            window.alert(`Some Error Occured`);
           }
      })
     
  }
}
