import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILookupItem } from 'app/shared/model/lookup-item.model';

@Component({
  selector: 'rv-lookup-item-detail',
  templateUrl: './lookup-item-detail.component.html'
})
export class LookupItemDetailComponent implements OnInit {
  lookupItem: ILookupItem;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ lookupItem }) => {
      this.lookupItem = lookupItem;
    });
  }

  previousState() {
    window.history.back();
  }
}
