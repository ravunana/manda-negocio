import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { MandaTestModule } from '../../../test.module';
import { RegraMovimentacaoCreditoComponent } from 'app/entities/regra-movimentacao-credito/regra-movimentacao-credito.component';
import { RegraMovimentacaoCreditoService } from 'app/entities/regra-movimentacao-credito/regra-movimentacao-credito.service';
import { RegraMovimentacaoCredito } from 'app/shared/model/regra-movimentacao-credito.model';

describe('Component Tests', () => {
  describe('RegraMovimentacaoCredito Management Component', () => {
    let comp: RegraMovimentacaoCreditoComponent;
    let fixture: ComponentFixture<RegraMovimentacaoCreditoComponent>;
    let service: RegraMovimentacaoCreditoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [RegraMovimentacaoCreditoComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(RegraMovimentacaoCreditoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RegraMovimentacaoCreditoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegraMovimentacaoCreditoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RegraMovimentacaoCredito(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.regraMovimentacaoCreditos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RegraMovimentacaoCredito(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.regraMovimentacaoCreditos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page is the page is the same as the previous page', () => {
      spyOn(service, 'query').and.callThrough();

      // WHEN
      comp.loadPage(0);

      // THEN
      expect(service.query).toHaveBeenCalledTimes(0);
    });

    it('should re-initialize the page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RegraMovimentacaoCredito(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);
      comp.clear();

      // THEN
      expect(comp.page).toEqual(0);
      expect(service.query).toHaveBeenCalledTimes(2);
      expect(comp.regraMovimentacaoCreditos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
