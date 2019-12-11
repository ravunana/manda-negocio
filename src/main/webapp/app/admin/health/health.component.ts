import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { RvHealthService } from './health.service';
import { RvHealthModalComponent } from './health-modal.component';

@Component({
  selector: 'rv-health',
  templateUrl: './health.component.html'
})
export class RvHealthCheckComponent implements OnInit {
  healthData: any;
  updatingHealth: boolean;

  constructor(private modalService: NgbModal, private healthService: RvHealthService) {}

  ngOnInit() {
    this.refresh();
  }

  baseName(name: string) {
    return this.healthService.getBaseName(name);
  }

  getBadgeClass(statusState) {
    if (statusState === 'UP') {
      return 'badge-success';
    } else {
      return 'badge-danger';
    }
  }

  refresh() {
    this.updatingHealth = true;

    this.healthService.checkHealth().subscribe(
      health => {
        this.healthData = this.healthService.transformHealthData(health);
        this.updatingHealth = false;
      },
      error => {
        if (error.status === 503) {
          this.healthData = this.healthService.transformHealthData(error.error);
          this.updatingHealth = false;
        }
      }
    );
  }

  showHealth(health: any) {
    const modalRef = this.modalService.open(RvHealthModalComponent);
    modalRef.componentInstance.currentHealth = health;
  }

  subSystemName(name: string) {
    return this.healthService.getSubSystemName(name);
  }
}
