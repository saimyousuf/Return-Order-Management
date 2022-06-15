import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ComponentProcessingService } from '../services/component-processing.service';

@Component({
  selector: 'app-cancel-request',
  templateUrl: './cancel-request.component.html',
  styleUrls: ['./cancel-request.component.css'],
})
export class CancelRequestComponent implements OnInit {
  constructor(
    private router: Router,
    private componentProcessingService: ComponentProcessingService
  ) {}

  responseRequests: any[] = [];
  processRequest: any[] = [];

  deleteResponseDetailObject: any = {};
  responseRequestObject: any = {};

  ngOnInit(): void {
    if (!localStorage.getItem('token')) {
      this.router.navigate(['/']);
    }

    this.getResponseRequests();

    this.getProcessRequests();
  }

  getResponseRequests() {
    this.componentProcessingService
      .getProcessingDetails()
      .subscribe((details) => {
        this.responseRequests = details;
      });
  }

  getProcessRequests() {
    this.componentProcessingService
      .getAllRequests()
      .subscribe((processRequests) => {
        this.processRequest = processRequests;
      });
  }

  fetchSingleResponse(event: any) {
    let requestId = event.target.value;

    this.responseRequests.forEach((rr) => {
      if (rr.requestId === requestId) {
        this.deleteResponseDetailObject.requestId = rr.requestId;
        this.deleteResponseDetailObject.processingCharge = rr.processingCharge;
        this.deleteResponseDetailObject.packagingAndDeliveryCharge =
          rr.packagingAndDeliveryCharge;
        this.deleteResponseDetailObject.dateOfDelivery = rr.dateOfDelivery;
        return;
      }
    });
  }

  fetchSingleRequest(event: any) {
    let creditCardNumber = event.target.value;

    this.processRequest.forEach((pr) => {
      if (pr.creditCardNumber == creditCardNumber) {
        this.responseRequestObject.creditCardNumber = pr.creditCardNumber;
        this.responseRequestObject.userName = pr.userName;
        this.responseRequestObject.contactNumber = pr.contactNumber;
        this.responseRequestObject.componentName = pr.componentName;
        this.responseRequestObject.componentType = pr.componentType;
        return;
      }
    });
  }

  removeRequest(requestId: any, creditCardNumber: any) {
    this.componentProcessingService
      .deleteRequest(requestId, creditCardNumber)
      .subscribe((res) => {
        if (res.message) {
          window.alert('Request Cancelled Successfully !!');
          this.getResponseRequests();
          this.getProcessRequests();
          this.router.navigate(['/dashboard/cancelRequest']).then(() => {
            window.location.reload();
          });
        } else {
          window.alert('Something Went Wrong');
        }
      });
  }
}
