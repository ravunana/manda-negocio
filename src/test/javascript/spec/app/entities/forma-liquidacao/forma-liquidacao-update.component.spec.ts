import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { FormaLiquidacaoUpdateComponent } from 'app/entities/forma-liquidacao/forma-liquidacao-update.component';
import { FormaLiquidacaoService } from 'app/entities/forma-liquidacao/forma-liquidacao.service';
import { FormaLiquidacao } from 'app/shared/model/forma-liquidacao.model';

describe('Component Tests', () => {
  describe('FormaLiquidacao Management Update Component', () => {
    let comp: FormaLiquidacaoUpdateComponent;
    let fixture: ComponentFixture<FormaLiquidacaoUpdateComponent>;
    let service: FormaLiquidacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [FormaLiquidacaoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FormaLiquidacaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FormaLiquidacaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FormaLiquidacaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FormaLiquidacao(123);
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
        const entity = new FormaLiquidacao();
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
