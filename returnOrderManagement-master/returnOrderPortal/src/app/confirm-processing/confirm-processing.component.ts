import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ComponentProcessingService } from '../services/component-processing.service';
@Component({
  selector: 'app-confirm-processing',
  templateUrl: './confirm-processing.component.html',
  styleUrls: ['./confirm-processing.component.css'],
})
export class ConfirmProcessingComponent implements OnInit {
  responseRequests: any[] = [];
  processRequest: any[] = [];
  processCharge: number = 0;
  showAlert: boolean = false;
  responseMessage: string = '';

  constructor(
    private router: Router,
    private componentProcessingService: ComponentProcessingService
  ) {}

  ngOnInit(): void {
    if (!localStorage.getItem('token')) {
      this.router.navigate(['/']);
    }

    this.getResponseRequests();

    this.getProcessRequests();
  }

  processingForm(completeProcessingForm: any) {
    let values = completeProcessingForm.value;
    let data = {
      requestId: values.requestId,
      creditCardNumber: parseInt(values.creditCardNumber),
      CreditLimit: values.CreditLimit,
      totalProcessingCharges: values.totalProcessingCharges,
    };

    this.componentProcessingService
      .CompleteProcessing(data)
      .subscribe((res) => {
        this.responseMessage = res.message;
        this.showAlert = true;

        setInterval(() => {
          this.showAlert = false;
        }, 6000);
      });
    completeProcessingForm.reset();
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

  fetchProcessCharge(event: any) {
    const requestId = event.target.value;

    this.responseRequests.forEach((rr) => {
      if (rr.requestId == requestId) {
        this.processCharge = rr.processingCharge;
        return;
      }
    });
  }
}
