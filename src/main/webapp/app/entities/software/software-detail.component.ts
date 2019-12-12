import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISoftware } from 'app/shared/model/software.model';

@Component({
  selector: 'rv-software-detail',
  templateUrl: './software-detail.component.html'
})
export class SoftwareDetailComponent implements OnInit {
  software: ISoftware;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ software }) => {
      this.software = software;
    });
  }

  previousState() {
    window.history.back();
  }
}
