import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { MeioLiquidacaoUpdateComponent } from 'app/entities/meio-liquidacao/meio-liquidacao-update.component';
import { MeioLiquidacaoService } from 'app/entities/meio-liquidacao/meio-liquidacao.service';
import { MeioLiquidacao } from 'app/shared/model/meio-liquidacao.model';

describe('Component Tests', () => {
  describe('MeioLiquidacao Management Update Component', () => {
    let comp: MeioLiquidacaoUpdateComponent;
    let fixture: ComponentFixture<MeioLiquidacaoUpdateComponent>;
    let service: MeioLiquidacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [MeioLiquidacaoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MeioLiquidacaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MeioLiquidacaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MeioLiquidacaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MeioLiquidacao(123);
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
        const entity = new MeioLiquidacao();
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
