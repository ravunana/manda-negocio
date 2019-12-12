import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { LookupItemDetailComponent } from 'app/entities/lookup-item/lookup-item-detail.component';
import { LookupItem } from 'app/shared/model/lookup-item.model';

describe('Component Tests', () => {
  describe('LookupItem Management Detail Component', () => {
    let comp: LookupItemDetailComponent;
    let fixture: ComponentFixture<LookupItemDetailComponent>;
    const route = ({ data: of({ lookupItem: new LookupItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [LookupItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LookupItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LookupItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lookupItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
