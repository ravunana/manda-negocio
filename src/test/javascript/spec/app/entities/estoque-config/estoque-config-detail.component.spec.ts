import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { EstoqueConfigDetailComponent } from 'app/entities/estoque-config/estoque-config-detail.component';
import { EstoqueConfig } from 'app/shared/model/estoque-config.model';

describe('Component Tests', () => {
  describe('EstoqueConfig Management Detail Component', () => {
    let comp: EstoqueConfigDetailComponent;
    let fixture: ComponentFixture<EstoqueConfigDetailComponent>;
    const route = ({ data: of({ estoqueConfig: new EstoqueConfig(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [EstoqueConfigDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EstoqueConfigDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstoqueConfigDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.estoqueConfig).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
