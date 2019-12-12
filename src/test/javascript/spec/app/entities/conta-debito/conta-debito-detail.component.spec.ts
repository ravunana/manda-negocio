import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { ContaDebitoDetailComponent } from 'app/entities/conta-debito/conta-debito-detail.component';
import { ContaDebito } from 'app/shared/model/conta-debito.model';

describe('Component Tests', () => {
  describe('ContaDebito Management Detail Component', () => {
    let comp: ContaDebitoDetailComponent;
    let fixture: ComponentFixture<ContaDebitoDetailComponent>;
    const route = ({ data: of({ contaDebito: new ContaDebito(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ContaDebitoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ContaDebitoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContaDebitoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contaDebito).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
