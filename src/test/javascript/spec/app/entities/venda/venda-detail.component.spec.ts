import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { VendaDetailComponent } from 'app/entities/venda/venda-detail.component';
import { Venda } from 'app/shared/model/venda.model';

describe('Component Tests', () => {
  describe('Venda Management Detail Component', () => {
    let comp: VendaDetailComponent;
    let fixture: ComponentFixture<VendaDetailComponent>;
    const route = ({ data: of({ venda: new Venda(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [VendaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VendaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VendaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.venda).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
