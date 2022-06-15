import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component"
import {DashboardComponent} from "./dashboard/dashboard.component"
import {ChangePasswordComponent} from "./change-password/change-password.component"
import {ProvideDetailsForReturnOrderComponent} from "./provide-details-for-return-order/provide-details-for-return-order.component"
import {ViewProcessingDetailComponent} from "./view-processing-detail/view-processing-detail.component"
import {ConfirmProcessingComponent} from "./confirm-processing/confirm-processing.component"
import {TrackRequestComponent} from "./track-request/track-request.component"
import {CancelRequestComponent} from "./cancel-request/cancel-request.component"

const routes: Routes = [
  {path : "" , component: LoginComponent},
  {path : "dashboard" , component: DashboardComponent},
  {path : "dashboard/changePassword" , component: ChangePasswordComponent},
  {path : "dashboard/provideDetails" , component: ProvideDetailsForReturnOrderComponent},
  {path : "dashboard/processingDetails" , component : ViewProcessingDetailComponent},
  {path : "dashboard/confirmProcessing" , component : ConfirmProcessingComponent},
  {path : "dashboard/trackRequest" , component : TrackRequestComponent},
  {path : "dashboard/cancelRequest" , component : CancelRequestComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
