import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { NavbarComponent } from './navbar/navbar.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HttpClientModule } from '@angular/common/http';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { LoginService } from './services/login.service';
import { ComponentProcessingService } from './services/component-processing.service';
import { ProvideDetailsForReturnOrderComponent } from './provide-details-for-return-order/provide-details-for-return-order.component';
import { ViewProcessingDetailComponent } from './view-processing-detail/view-processing-detail.component';
import { ConfirmProcessingComponent } from './confirm-processing/confirm-processing.component';
import { TrackRequestComponent } from './track-request/track-request.component';
import { CancelRequestComponent } from './cancel-request/cancel-request.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavbarComponent,
    DashboardComponent,
    ChangePasswordComponent,
    ProvideDetailsForReturnOrderComponent,
    ViewProcessingDetailComponent,
    ConfirmProcessingComponent,
    TrackRequestComponent,
    CancelRequestComponent,
  ],
  imports: [BrowserModule, AppRoutingModule, FormsModule, HttpClientModule],
  providers: [
    LoginService,
    ComponentProcessingService
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
