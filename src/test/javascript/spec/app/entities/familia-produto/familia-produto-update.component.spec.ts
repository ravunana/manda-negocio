import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { FamiliaProdutoUpdateComponent } from 'app/entities/familia-produto/familia-produto-update.component';
import { FamiliaProdutoService } from 'app/entities/familia-produto/familia-produto.service';
import { FamiliaProduto } from 'app/shared/model/familia-produto.model';

describe('Component Tests', () => {
  describe('FamiliaProduto Management Update Component', () => {
    let comp: FamiliaProdutoUpdateComponent;
    let fixture: ComponentFixture<FamiliaProdutoUpdateComponent>;
    let service: FamiliaProdutoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [FamiliaProdutoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FamiliaProdutoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FamiliaProdutoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FamiliaProdutoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FamiliaProduto(123);
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
        const entity = new FamiliaProduto();
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
