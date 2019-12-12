import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { RelacionamentoPessoaUpdateComponent } from 'app/entities/relacionamento-pessoa/relacionamento-pessoa-update.component';
import { RelacionamentoPessoaService } from 'app/entities/relacionamento-pessoa/relacionamento-pessoa.service';
import { RelacionamentoPessoa } from 'app/shared/model/relacionamento-pessoa.model';

describe('Component Tests', () => {
  describe('RelacionamentoPessoa Management Update Component', () => {
    let comp: RelacionamentoPessoaUpdateComponent;
    let fixture: ComponentFixture<RelacionamentoPessoaUpdateComponent>;
    let service: RelacionamentoPessoaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [RelacionamentoPessoaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RelacionamentoPessoaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RelacionamentoPessoaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RelacionamentoPessoaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RelacionamentoPessoa(123);
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
        const entity = new RelacionamentoPessoa();
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
