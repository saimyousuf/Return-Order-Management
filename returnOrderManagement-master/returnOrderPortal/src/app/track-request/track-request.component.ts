import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ComponentProcessingService } from '../services/component-processing.service';
@Component({
  selector: 'app-track-request',
  templateUrl: './track-request.component.html',
  styleUrls: ['./track-request.component.css'],
})
export class TrackRequestComponent implements OnInit {
  trackResponse: string = '';
  deliveryDate: Date | undefined = undefined;

  showAlert: boolean = false;

  constructor(
    private router: Router,
    private componentProcessingService: ComponentProcessingService
  ) {}

  ngOnInit(): void {
    if (!localStorage.getItem('token')) {
      this.router.navigate(['/']);
    }
  }

  trackRequest(formData: any) {
    
    const requestId = formData.value.requestId;
    this.componentProcessingService
      .trackRequest(requestId)
      .subscribe((response) => {

        let a = response.message.split(' ');
       
        if (a.length > 7) {
          let dateString = a[9];
          this.deliveryDate = new Date(dateString);
          let str = '';

          for (let i = 0; i < a.length - 1; i++) {
            str += ' ' + a[i];
          }

          this.trackResponse = str;
        } else {
        
          this.trackResponse = "Id not Found!";
          this.deliveryDate = undefined;
        }

        this.showAlert = true;

        setInterval(() => {
          this.showAlert = false;
        }, 15000);
      });

    formData.reset();
  }
}
