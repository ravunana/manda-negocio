import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { AuditoriaCompraDetailComponent } from 'app/entities/auditoria-compra/auditoria-compra-detail.component';
import { AuditoriaCompra } from 'app/shared/model/auditoria-compra.model';

describe('Component Tests', () => {
  describe('AuditoriaCompra Management Detail Component', () => {
    let comp: AuditoriaCompraDetailComponent;
    let fixture: ComponentFixture<AuditoriaCompraDetailComponent>;
    const route = ({ data: of({ auditoriaCompra: new AuditoriaCompra(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [AuditoriaCompraDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AuditoriaCompraDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AuditoriaCompraDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.auditoriaCompra).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
