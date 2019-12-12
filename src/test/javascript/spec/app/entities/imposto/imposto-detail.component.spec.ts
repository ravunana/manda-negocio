import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { ImpostoDetailComponent } from 'app/entities/imposto/imposto-detail.component';
import { Imposto } from 'app/shared/model/imposto.model';

describe('Component Tests', () => {
  describe('Imposto Management Detail Component', () => {
    let comp: ImpostoDetailComponent;
    let fixture: ComponentFixture<ImpostoDetailComponent>;
    const route = ({ data: of({ imposto: new Imposto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ImpostoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ImpostoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ImpostoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.imposto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
