import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { VariacaoProdutoUpdateComponent } from 'app/entities/variacao-produto/variacao-produto-update.component';
import { VariacaoProdutoService } from 'app/entities/variacao-produto/variacao-produto.service';
import { VariacaoProduto } from 'app/shared/model/variacao-produto.model';

describe('Component Tests', () => {
  describe('VariacaoProduto Management Update Component', () => {
    let comp: VariacaoProdutoUpdateComponent;
    let fixture: ComponentFixture<VariacaoProdutoUpdateComponent>;
    let service: VariacaoProdutoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [VariacaoProdutoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VariacaoProdutoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VariacaoProdutoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VariacaoProdutoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new VariacaoProduto(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new VariacaoProduto();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
